package es.jac.gymlog.fragments.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import es.jac.gymlog.R
import es.jac.gymlog.managers.AuthManager
import es.jac.gymlog.databinding.FragmentResetPasswordBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ResetPasswordFragment : Fragment() {


    private lateinit var binding: FragmentResetPasswordBinding
    private lateinit var mListener: ResetPasswordListener
    private lateinit var authManager: AuthManager


    override fun onAttach(context: Context) {
        super.onAttach(context)

        authManager = AuthManager()
        if(context is ResetPasswordListener){
            mListener = context
        }else{
            throw Exception("Your fragment or activity must implement the interface ResetPasswordListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResetPasswordBinding.inflate(inflater,container,false)

        binding.btnSenEmail.setOnClickListener {
            val email = binding.titUser.text.toString()

            if(!email.isNullOrBlank()){
                lifecycleScope.launch(Dispatchers.IO){
                    authManager.resetPass(email)
                    withContext(Dispatchers.Main){
                        Toast.makeText(requireContext(), R.string.email_enviado, Toast.LENGTH_SHORT).show()
                        mListener.onSendEmailBtnClicked()
                    }
                }
            }
        }
        return binding.root
    }

    interface ResetPasswordListener{
        fun onSendEmailBtnClicked()
    }


}