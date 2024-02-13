package es.jac.gymlog.managers

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import es.jac.gymlog.models.Mensaje
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import es.jac.gymlog.models.Usuario

class FirestoreManager {
    private val firestore by lazy { FirebaseFirestore.getInstance() }
    private val auth = AuthManager()
    private val userId = auth.getCurrentUser()?.email


    suspend fun addMensaje(msj: Mensaje): Boolean {
        return try {
            msj.createdData = Timestamp.now().toDate()
            msj.idRemitente = userId.toString()
            firestore.collection(MENSAJE_COLLECTION).add(msj).await()
            true
        }catch(e: Exception){
            false
        }
    }

    suspend fun getMensajesFlow(): Flow<List<Mensaje>> = callbackFlow{
        var mensajeCollection: CollectionReference? = null
        try {
            mensajeCollection = FirebaseFirestore.getInstance()
                .collection(MENSAJE_COLLECTION)

            val subscription = mensajeCollection?.orderBy(CREATED_DATA, Query.Direction.ASCENDING)?.addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val mensajes = mutableListOf<Mensaje>()
                    snapshot.forEach{
                        mensajes.add(
                            Mensaje(
                                id = it.get(MENSAJE_ID).toString(),
                                idRemitente = it.get(MENSAJE_REMITENTE).toString(),
                                mensaje = it.get(MENSAJE_CONTENT).toString()
                            )
                        )
                    }
                    trySend(mensajes)
                }
            }
            awaitClose { subscription?.remove() }
        } catch (e: Throwable) {
            close(e)
        }
    }

    suspend fun getNombresChat(): Flow<List<Usuario>> = callbackFlow{
        var nombresCollection: CollectionReference? = null
        try {
            nombresCollection = FirebaseFirestore.getInstance()
                .collection(NOMBRE_CHAT_COLLECTION)

            val subscription = nombresCollection?.addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val usuarios = mutableListOf<Usuario>()
                    snapshot.forEach{
                        usuarios.add(
                            Usuario(
                                idUsuario = it.get(ID_USUARIO).toString(),
                                nombreChat = it.get(NOMBRE_CHAT).toString()
                            )
                        )
                    }
                    trySend(usuarios)
                }
            }
            awaitClose { subscription?.remove() }
        } catch (e: Throwable) {
            close(e)
        }
    }

    suspend fun updateNombreChat(usuario: Usuario): Boolean {
        return try {
            val userRef = usuario.idUsuario.let { firestore.collection(NOMBRE_CHAT_COLLECTION).document(it) }
            userRef?.set(usuario)?.await()
            true
        }catch(e: Exception){
            false
        }
    }

    companion object{
        const val MENSAJE_COLLECTION = "mensajes"
        const val MENSAJE_REMITENTE = "idRemitente"
        const val MENSAJE_CONTENT = "mensaje"
        const val MENSAJE_ID = "id"
        const val CREATED_DATA = "createdData"
        const val NOMBRE_CHAT_COLLECTION = "nombres_chat"
        const val ID_USUARIO = "idUsuario"
        const val NOMBRE_CHAT = "nombreChat"
    }
}