package es.jac.gymlog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import es.jac.gymlog.managers.AuthManager
import es.jac.gymlog.fragments.login.CreateAccountFragment
import es.jac.gymlog.fragments.login.LogInFragment
import es.jac.gymlog.fragments.login.MenuLogInFragment
import es.jac.gymlog.fragments.login.ResetPasswordFragment

class LogInActivity : AppCompatActivity(), MenuLogInFragment.MenuLogInFragmentListener,
    LogInFragment.FragmentLogInListener, CreateAccountFragment.CreateAccountListener, ResetPasswordFragment.ResetPasswordListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(AuthManager().getCurrentUser() != null){
            onLogInBtnClicked()
        }


    }



    //Funciones del MenuFragment (LogIn/Registro)
    override fun onLoginBtnMenuClicked() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<LogInFragment>(R.id.fragmentContainerView_login)
            addToBackStack(null)
        }
    }

    override fun onCreateAccountBtnMenuClicked() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<CreateAccountFragment>(R.id.fragmentContainerView_login)
            addToBackStack(null)
        }
    }


    //Funciones del LogIn fragment
    override fun onLogInBtnClicked() {
        val intent = Intent(this,WorkoutActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onForgotPassClicked() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<ResetPasswordFragment>(R.id.fragmentContainerView_login)
            addToBackStack(null)
        }
    }


    //Funciones del CreateAccountFragment
    override fun onRegisteredBtnClicked() {
        val intent = Intent(this,WorkoutActivity::class.java)
        startActivity(intent)
    }

    override fun onSendEmailBtnClicked() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<LogInFragment>(R.id.fragmentContainerView_login)
            addToBackStack(null)
        }
    }

}