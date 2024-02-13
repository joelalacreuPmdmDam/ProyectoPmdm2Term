package es.jac.gymlog

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.navigation.NavigationView
import es.jac.gymlog.managers.AuthManager
import es.jac.gymlog.fragments.InfoUserFragment
import es.jac.gymlog.fragments.dialogs.LogOutDialogFragment
import es.jac.gymlog.fragments.RmCalculatorFragment
import es.jac.gymlog.fragments.TimerFragment
import es.jac.gymlog.fragments.WorkoutFragment
import es.jac.gymlog.databinding.ActivityWorkoutBinding
import es.jac.gymlog.fragments.ChatFragment
import es.jac.gymlog.fragments.retrofit_fragment.ExerciseDetailFragment
import es.jac.gymlog.fragments.retrofit_fragment.ListaEjerciciosFragment

class WorkoutActivity : AppCompatActivity(), ListaEjerciciosFragment.ExerciseListListener,
    LogOutDialogFragment.LogOutDialogListener, NavigationView.OnNavigationItemSelectedListener,
    InfoUserFragment.InfoUserListener{

    companion object{
        private val ACTIVITY_REQUEST_CODE = 200
    }

    private lateinit var binding: ActivityWorkoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.workoutToolbar)
        setUpNavigationDrawer()
    }

    override fun onResume() {
        super.onResume()
        val prefs = getSharedPreferences("es.jac.gymlog_preferences", MODE_PRIVATE)
        val nombre = prefs.getString("username","")
        //Acceder al txt del header que contiene el nombre
        val headerNavigationView = binding.navigationView.getHeaderView(0)
        val txtNombreHeader = headerNavigationView.findViewById<TextView>(R.id.nav_myName)
        txtNombreHeader.text = nombre
        //Acceder al txt del header que contiene el email
        val txtEmailHeader = headerNavigationView.findViewById<TextView>(R.id.nav_myEmail)
        txtEmailHeader.text = AuthManager().getCurrentUser()?.email

    }


    //Creacion de la toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.workout_menu,menu)
        return true
    }

    //Metodo onCLick de la toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.add_workout -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<ListaEjerciciosFragment>(R.id.fragmentContainerView_workoutActivity)
                    addToBackStack(null)
                }
                true
            }
            R.id.log_outBtn -> {
                LogOutDialogFragment().show(this.supportFragmentManager,"")
                true
            }
            else -> false
        }
    }




    //NavigationDrawer config start
    private fun setUpNavigationDrawer() {
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.workoutToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        binding.drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()
        binding.navigationView?.setNavigationItemSelectedListener(this)

        //Check the first option
        //binding.navigationView.setCheckedItem(R.id.nav_camera)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.drawerLayout?.closeDrawer(GravityCompat.START)

        return when(item.itemId) {
            R.id.home_nav_option -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<WorkoutFragment>(R.id.fragmentContainerView_workoutActivity)
                }
                return true
            }

            R.id.rm_calculator_nav_option -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<RmCalculatorFragment>(R.id.fragmentContainerView_workoutActivity)
                }
                return true
            }

            R.id.timer_nav_option -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<TimerFragment>(R.id.fragmentContainerView_workoutActivity)
                }
                return true
            }

            R.id.chat_nav_option -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<ChatFragment>(R.id.fragmentContainerView_workoutActivity)
                }
                return true
            }

            R.id.personal_information_nav_option -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<InfoUserFragment>(R.id.fragmentContainerView_workoutActivity)
                }
                return true
            }

            R.id.settings_nav_option -> {
                val intent = Intent(this,SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> {
                return false
            }
        }

    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            onBackPressedDispatcher.onBackPressed()
        }
    }

    //NavigationDrawer config end


    //Boton 'OK' del  dialogo
    override fun onDialogPositiveBtnClicked() {
        AuthManager().logOut()
        val intent = Intent(this,LogInActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onExportarBtnClicked() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
            != PackageManager.PERMISSION_GRANTED){
            //Pedir permiso
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                ACTIVITY_REQUEST_CODE
            )
        }else{ //Permission granted
            Toast.makeText(this,"Permiso concedido",Toast.LENGTH_SHORT).show()
        }
    }

    //Permisos
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == ACTIVITY_REQUEST_CODE) {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permiso concedido",Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this,"Permiso no concedido",Toast.LENGTH_SHORT).show()
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onSearchBtnClicked() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<ExerciseDetailFragment>(R.id.fragmentContainerView_workoutActivity)
            addToBackStack(null)
        }
    }

    override fun onInfoBtnClicked() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<ExerciseDetailFragment>(R.id.fragmentContainerView_workoutActivity)
            addToBackStack(null)
        }
    }


}