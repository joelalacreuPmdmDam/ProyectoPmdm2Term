package es.jac.gymlog.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import es.jac.gymlog.Classes.InfoApp
import es.jac.gymlog.R
import es.jac.gymlog.databinding.FragmentCreateAccountBinding

class CreateAccountFragment : Fragment() {

    private lateinit var miListener: CreateAccountListener
    private lateinit var binding: FragmentCreateAccountBinding
    val infoApp: InfoApp = InfoApp()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateAccountBinding.inflate(inflater,container,false)
        binding.btnRegistrarUsuario.setOnClickListener{
            val usuario = binding.titUser.text.toString()
            val password = binding.titPass.text.toString()

            if (usuario.isNotEmpty() && password.isNotEmpty()){ //Comprobamos que los campos no estén vacios
                if (comprobarUsuario(usuario) == true){ //Comprobamos si el usuario ya existe
                    miListener.onRegisteredBtnClicked()
                }else{
                    Snackbar.make(binding.root,"Este usuario ya está registrado", Snackbar.LENGTH_SHORT).show()
                }
            }else{
                Snackbar.make(binding.root,"Campos obligatorios", Snackbar.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    fun comprobarUsuario(usuario: String):Boolean{
        val listaUsuarios = infoApp.listaUsuarios

        for (usuarioLista in listaUsuarios){
            if (usuario == usuarioLista.nombre){
                return false
            }
        }
        return true
    }

    interface CreateAccountListener{
        fun onRegisteredBtnClicked()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is CreateAccountListener){
            miListener = context
        }else{
            throw Exception("Your fragment or activity must implement the interface CreateAccountListener")
        }
    }


}