package es.jac.gymlog

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import es.jac.gymlog.Classes.CategoriaEjercicio
import es.jac.gymlog.Fragments.BenchPressFragment
import es.jac.gymlog.Fragments.CategoriasEjerciciosList
import es.jac.gymlog.Fragments.InfoUserFragment
import es.jac.gymlog.Fragments.LogOutDialogFragment
import es.jac.gymlog.Fragments.RmCalculatorFragment
import es.jac.gymlog.Fragments.SealRowFragment
import es.jac.gymlog.Fragments.SquatFragment
import es.jac.gymlog.Fragments.TimerFragment
import es.jac.gymlog.Fragments.WorkoutFragment
import es.jac.gymlog.databinding.ActivityWorkoutBinding

class WorkoutActivity : AppCompatActivity(), CategoriasEjerciciosList.CategoriasListListener,
    WorkoutFragment.OnBtnBottomBarClicked,LogOutDialogFragment.LogOutDialogListener,
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
                    replace<CategoriasEjerciciosList>(R.id.fragmentContainerView_workoutActivity)
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

    //OnClick item lista ejercicios
    override fun onItemClick(categoria: CategoriaEjercicio) {
        if (categoria.idCategoria == 1){
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<BenchPressFragment>(R.id.fragmentContainerView_workoutActivity)
                    addToBackStack(null)
                }
        }else if(categoria.idCategoria == 2){
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<SealRowFragment>(R.id.fragmentContainerView_workoutActivity)
                addToBackStack(null)
            }
        }else{
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<SquatFragment>(R.id.fragmentContainerView_workoutActivity)
                addToBackStack(null)
            }
        }
    }

    //Start botones de la bottom  bar
    override fun itemCalculadoraRmClicked() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RmCalculatorFragment>(R.id.fragmentContainerView_workoutActivity)
            addToBackStack(null)
        }
    }

    override fun itemTimerClicked() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<TimerFragment>(R.id.fragmentContainerView_workoutActivity)
            addToBackStack(null)
        }

    }

    override fun itemPersonalInfoClicked() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<InfoUserFragment>(R.id.fragmentContainerView_workoutActivity)
            addToBackStack(null)
        }
    }
    //END


    //Boton 'OK' del  dialogo
    override fun onDialogPositiveBtnClicked() {
        val intent = Intent(this,LogInActivity::class.java)
        startActivity(intent)
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


}