package es.jac.gymlog.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.jac.gymlog.R
import es.jac.gymlog.managers.AuthManager
import es.jac.gymlog.databinding.FragmentInfoUserBinding



class InfoUserFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentInfoUserBinding
    private lateinit var mListener: InfoUserListener


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is InfoUserListener){
            mListener = context
        }else{
            throw Exception("The activity must implement thee interface InfoUserListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoUserBinding.inflate(inflater,container,false)
        binding.etInfoMail?.setText(AuthManager().getCurrentUser()!!?.email.toString())
        binding.btnExportar.setOnClickListener(this)
        return binding.root
    }

    interface InfoUserListener{
        fun onExportarBtnClicked()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnExportar ->{mListener.onExportarBtnClicked()}
        }
    }


}