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
import es.jac.gymlog.databinding.FragmentLogInBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding
    private lateinit var mListener: FragmentLogInListener
    private lateinit var authManager: AuthManager

    override fun onAttach(context: Context) {
        super.onAttach(context)

        authManager = AuthManager()
        if (context is FragmentLogInListener){
            mListener = context
        }else{
            throw Exception("Your fragment or activity must implement the interface FragmentLogInListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(inflater,container,false)

        binding.btnLogIn.setOnClickListener{

            val usuario = binding.titUser.text.toString()
            val password = binding.titPass.text.toString()

            if(!usuario.isNullOrBlank() && !password.isNullOrBlank()){
                lifecycleScope.launch(Dispatchers.IO){
                    val userLogged = authManager.login(usuario,password)
                    withContext(Dispatchers.Main){
                        if(userLogged != null){
                            Toast.makeText(requireContext(), userLogged.email, Toast.LENGTH_SHORT).show()
                            mListener.onLogInBtnClicked()
                        }else{
                            Toast.makeText(requireContext(), R.string.bad_credentials, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        binding.tvOlvidarContrasenya?.setOnClickListener{
            mListener.onForgotPassClicked()
        }

        return binding.root
    }



    interface FragmentLogInListener{
        fun onLogInBtnClicked()
        fun onForgotPassClicked()
    }




}