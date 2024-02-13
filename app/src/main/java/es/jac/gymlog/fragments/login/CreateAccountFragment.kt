package es.jac.gymlog.fragments.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import es.jac.gymlog.managers.AuthManager
import es.jac.gymlog.databinding.FragmentCreateAccountBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateAccountFragment : Fragment() {

    private lateinit var mListener: CreateAccountListener
    private lateinit var binding: FragmentCreateAccountBinding
    private lateinit var authManager: AuthManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateAccountBinding.inflate(inflater,container,false)
        binding.btnRegistrarUsuario.setOnClickListener{
            val email = binding.titUser.text.toString()
            val pass = binding.titPass.text.toString()

            if(!email.isNullOrBlank() && !pass.isNullOrBlank()){
                lifecycleScope.launch(Dispatchers.IO){
                    val userLogged = authManager.signIn(email,pass)
                    withContext(Dispatchers.Main){
                        if(userLogged != null){
                            Toast.makeText(requireContext(), userLogged.email, Toast.LENGTH_SHORT).show()
                            mListener.onRegisteredBtnClicked()
                        }else{
                            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        return binding.root
    }



    interface CreateAccountListener{
        fun onRegisteredBtnClicked()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        authManager = AuthManager()
        if (context is CreateAccountListener){
            mListener = context
        }else{
            throw Exception("Your fragment or activity must implement the interface CreateAccountListener")
        }
    }


}