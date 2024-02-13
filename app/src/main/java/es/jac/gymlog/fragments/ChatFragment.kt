package es.jac.gymlog.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import es.jac.gymlog.R
import es.jac.gymlog.adapters.MensajesAdapter
import es.jac.gymlog.databinding.FragmentChatBinding
import es.jac.gymlog.managers.FirestoreManager
import es.jac.gymlog.managers.PushNotificationService
import es.jac.gymlog.models.Mensaje
import es.jac.gymlog.models.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ChatFragment : Fragment(){

    private lateinit var binding: FragmentChatBinding
    private lateinit var mensajesLista: MutableList<Mensaje>
    private lateinit var nombresChat: MutableList<Usuario>
    private lateinit var mAdapter: MensajesAdapter
    private val firestoreManager: FirestoreManager by lazy { FirestoreManager() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater,container,false)

        binding.btnEnviar.setOnClickListener{
            var mensaje = binding.etMensaje.text.toString()
            if (!mensaje.isNullOrBlank()){
                lifecycleScope.launch(Dispatchers.IO) {
                    firestoreManager.addMensaje(Mensaje(mensaje = mensaje))
                    withContext(Dispatchers.Main){
                        var notificationService = PushNotificationService(requireContext())
                        notificationService.createNotification("GymLog","You have new messages")
                        binding.etMensaje.text.clear()
                        binding.recViewItems.smoothScrollToPosition(mensajesLista.size-1)
                        mAdapter.notifyDataSetChanged()
                    }
                }
            }

        }
        setRecyclerView()
        return binding.root
    }

    private fun setRecyclerView(){
        mensajesLista = mutableListOf()
        nombresChat = mutableListOf()
        binding.recViewItems.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mAdapter = MensajesAdapter(requireContext(), mensajesLista, nombresChat)
        binding.recViewItems.adapter = mAdapter
        lifecycleScope.launch (Dispatchers.IO){
            getNombreChats()
            firestoreManager.getMensajesFlow()
                .collect{ notesUpdated ->
                    mensajesLista.clear()
                    mensajesLista.addAll(notesUpdated)
                    withContext(Dispatchers.Main){
                        mAdapter.notifyDataSetChanged()
                        binding.recViewItems.scrollToPosition(mensajesLista.size-1)
                    }
                }
        }


    }

    private fun getNombreChats(){
        lifecycleScope.launch (Dispatchers.IO){
            firestoreManager.getNombresChat()
                .collect{ nombresUpdated ->
                    nombresChat.clear()
                    nombresChat.addAll(nombresUpdated)
                    withContext(Dispatchers.Main){
                        mAdapter.notifyDataSetChanged()
                        binding.recViewItems.scrollToPosition(mensajesLista.size-1)
                    }
                }

        }

    }




}