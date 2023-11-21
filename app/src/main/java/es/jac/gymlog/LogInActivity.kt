package es.jac.gymlog

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import es.jac.gymlog.Fragments.CreateAccountFragment
import es.jac.gymlog.Fragments.LogInFragment
import es.jac.gymlog.Fragments.MenuLogInFragment
import android.Manifest
import android.widget.Toast

class LogInActivity : AppCompatActivity(),MenuLogInFragment.MenuLogInFragmentListener,
    LogInFragment.FragmentLogInListener, CreateAccountFragment.CreateAccountListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }



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

    override fun onLogInBtnClicked() {
        val intent = Intent(this,WorkoutActivity::class.java)
        startActivity(intent)
    }

    override fun onRegisteredBtnClicked() {
        val intent = Intent(this,WorkoutActivity::class.java)
        startActivity(intent)
    }

}