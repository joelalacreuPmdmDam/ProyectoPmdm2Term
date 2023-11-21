package es.jac.gymlog.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import es.jac.gymlog.Classes.InfoApp
import es.jac.gymlog.Classes.Usuario
import es.jac.gymlog.databinding.FragmentLogInBinding

class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding
    private lateinit var mListener: FragmentLogInListener
    private val infoApp: InfoApp = InfoApp()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(inflater,container,false)

        binding.btnLogIn.setOnClickListener{

            val usuario = binding.titUser.text.toString()
            val password = binding.titPass.text.toString()

            if (usuario.isNotEmpty() && password.isNotEmpty()){ //Comprobamos que los campos no estén vacios
                if (comprobarUsuario(usuario,password) == true){ //Comprobamos que las credenciales sean correctas
                    mListener.onLogInBtnClicked()
                }else{
                    Snackbar.make(binding.root,"Credenciales incorrectas", Snackbar.LENGTH_SHORT).show()
                }
            }else{
                Snackbar.make(binding.root,"Campos obligatorios", Snackbar.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    fun comprobarUsuario(usuario: String,contrasenya: String):Boolean{
        val credenciales = usuario+contrasenya
        val listaUsuarios = infoApp.listaUsuarios
        for (usuarioLista in listaUsuarios){
            val credencialesLista = usuarioLista.nombre+usuarioLista.contrasenya
            if (credenciales == credencialesLista){
                return true
            }
        }
        return false
    }

    interface FragmentLogInListener{
        fun onLogInBtnClicked()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentLogInListener){
            mListener = context
        }else{
            throw Exception("Your fragment or activity must implement the interface FragmentLogInListener")
        }
    }


}