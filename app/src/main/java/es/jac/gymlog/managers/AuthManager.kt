package es.jac.gymlog.managers

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

class AuthManager {

    private val auth: FirebaseAuth by lazy { Firebase.auth }

    suspend fun login(email:String, password: String): FirebaseUser?{
        return try {
            val authResult = auth.signInWithEmailAndPassword(email,password).await()
            authResult.user
        }catch (e:Exception){
            null
        }
    }

    suspend fun signIn(email: String, password: String): FirebaseUser?{
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email,password).await()
            authResult.user
        }catch (e:Exception){
            null
        }
    }

    suspend fun resetPass(email: String){
        try {
            auth.sendPasswordResetEmail(email).await()
        }catch (e:Exception){
            null
        }
    }

    fun logOut(){
        auth.signOut()
    }

    fun getCurrentUser(): FirebaseUser?{
        return auth.currentUser
    }
}