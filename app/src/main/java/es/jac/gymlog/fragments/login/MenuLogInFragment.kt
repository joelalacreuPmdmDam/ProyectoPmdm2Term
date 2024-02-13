package es.jac.gymlog.fragments.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.jac.gymlog.R
import es.jac.gymlog.databinding.FragmentMenuLogInBinding


class MenuLogInFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentMenuLogInBinding
    private var miListener: MenuLogInFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is MenuLogInFragmentListener){
            miListener = context
        }else{
            throw Exception("The activity must implement thee interface MenuLogInFragmentListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuLogInBinding.inflate(inflater,container,false)
        binding.btnIniciarSesionMenuLogIn.setOnClickListener(this)
        binding.btnRegistrarseMenuLogIn.setOnClickListener(this)
        return return binding.root
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_iniciarSesion_menuLogIn -> {miListener?.onLoginBtnMenuClicked()}
            R.id.btn_registrarse_menuLogIn -> {miListener?.onCreateAccountBtnMenuClicked()}
        }
    }

    override fun onDetach() {
        super.onDetach()
        miListener = null
    }

    interface MenuLogInFragmentListener{
        fun onLoginBtnMenuClicked()
        fun onCreateAccountBtnMenuClicked()
    }


}