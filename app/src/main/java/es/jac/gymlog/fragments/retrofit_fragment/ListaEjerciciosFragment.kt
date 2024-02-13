package es.jac.gymlog.fragments.retrofit_fragment

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import es.jac.gymlog.R
import es.jac.gymlog.adapters.ExercisesAdapter
import es.jac.gymlog.databinding.FragmentListaEjerciciosBinding
import es.jac.gymlog.managers.ExerciseAPI
import es.jac.gymlog.managers.RetrofitObject
import es.jac.gymlog.models.ExerciseResponse
import es.jac.gymlog.models.SharedViewModel
import es.jac.gymlog.room_database.ExerciseDb
import es.jac.gymlog.room_database.ExercisesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListaEjerciciosFragment : Fragment() {

    private lateinit var binding: FragmentListaEjerciciosBinding
    private lateinit var mAdapter: ExercisesAdapter
    private lateinit var listExercises: MutableList<ExerciseResponse>
    private lateinit var exerciseService: ExerciseAPI
    private lateinit var mListener: ExerciseListListener
    private val pass = "98UHheuU6o+l7imCXwtrvA==lHavoJSL38wu2qrl"
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var database: ExercisesDatabase

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ExerciseListListener){
            mListener = context
        }else{
            throw Exception("Your fragment or activity must implement the interface ExerciseListListener")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListaEjerciciosBinding.inflate(inflater,container,false)

        setUpRecycler()

        exerciseService = RetrofitObject.getInstance()
            .create(ExerciseAPI::class.java)

        //Crear room database
        //isDatabaseExist()
        database = Room.databaseBuilder(requireContext(),ExercisesDatabase::class.java, "GYMLOG_DB").build()





        if (!offlineActivated()){
            lifecycleScope.launch(Dispatchers.IO){
                database.exerciseDao().deleteAll()
                withContext(Dispatchers.Main){
                    mAdapter.notifyDataSetChanged()
                }
            }
        }

        binding.btnSearch.setOnClickListener {
            if (isConnectedToInternet()){
                searchExercise(binding.etBuscador.text.toString())

            }else{

                if (offlineActivated()){//No Internet y si offline mode
                    getExercises(binding.etBuscador.text.toString())
                }else{
                    Toast.makeText(requireContext(),R.string.noConexion,Toast.LENGTH_SHORT).show()
                }

            }

        }

        return binding.root
    }

    fun isDatabaseExist() {
        val dbFile = requireContext().getDatabasePath("GYMLOG_DB")
        if (!dbFile.exists()){
            database = Room.databaseBuilder(requireContext(),ExercisesDatabase::class.java, "GYMLOG_DB").build()
        }
    }

    fun offlineActivated():Boolean{
        val prefs = requireContext().getSharedPreferences("es.jac.gymlog_preferences",
            AppCompatActivity.MODE_PRIVATE
        )
        var isOfflineModeActivated : Boolean = prefs.getBoolean("offline_mode_activated", false)
        return isOfflineModeActivated
    }

    private fun setUpRecycler(){
        binding.recView.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))

        listExercises = emptyList<ExerciseResponse>().toMutableList()
        val clickFunct = {exercise : ExerciseResponse ->
            asignarDatosViewModel(exercise)
            mListener.onSearchBtnClicked()
        }


        val infoClickFunct: (ExerciseResponse?) -> Unit = { exercise ->
            asignarDatosViewModel(exercise!!)
            mListener.onInfoBtnClicked()

        }
        mAdapter = ExercisesAdapter(listExercises,clickFunct,infoClickFunct)
        binding.recView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        binding.recView.adapter = mAdapter
    }

    private fun searchExercise(muscle: String){
        lifecycleScope.launch(Dispatchers.IO) {
            val response = exerciseService.getExercises(muscle,pass)
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    val lista = response.body() ?: emptyList()
                    mAdapter.updateData(lista)
                    insertarEjerciciosDb(lista)
                } else {
                    Toast.makeText(requireContext(),"Error getting the exercises",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    interface ExerciseListListener{
        fun onSearchBtnClicked()
        fun onInfoBtnClicked()
    }

    fun asignarDatosViewModel(exercise: ExerciseResponse){
        sharedViewModel.exercise_name = exercise.name
        sharedViewModel.exercise_muscle = exercise.muscle
        sharedViewModel.exercise_type = exercise.type
        sharedViewModel.exercise_equipment = exercise.equipment
        sharedViewModel.exercise_difficulty = exercise.difficulty
        sharedViewModel.exercise_instructions = exercise.instructions
    }


    private fun isConnectedToInternet(): Boolean {
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo?.isConnected ?: false
        }
    }


    fun insertarEjerciciosDb(lista: List<ExerciseResponse>){
        lifecycleScope.launch (Dispatchers.IO) {
            lista.forEach {
                var nuevoEjercicioDb = ExerciseDb(name = it.name, type = it.type, difficulty = it.difficulty,
                    muscle = it.muscle, equipment = it.equipment, instructions = it.instructions)
                var ejercicioRepetido = database.exerciseDao().getOneExercise(it.name)
                if (ejercicioRepetido == null){
                    database.exerciseDao().insertExercise(nuevoEjercicioDb)
                }
            }
            withContext(Dispatchers.Main){

            }
        }

    }

    fun getExercises(muscle: String){
        lifecycleScope.launch (Dispatchers.IO) {
            Log.d("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",listExercises.size.toString())
            var lista : MutableList<ExerciseDb> = database.exerciseDao().getExerciseByMuscle(muscle)
            withContext(Dispatchers.Main){
                if (lista.size > 0){
                    var listaAdapter: MutableList<ExerciseResponse> = emptyList<ExerciseResponse>().toMutableList()
                    lista.forEach {
                        listaAdapter.add(
                            ExerciseResponse(name = it.name, type = it.type, difficulty = it.difficulty,
                                muscle = it.muscle, equipment = it.equipment, instructions = it.instructions)
                        )
                    }
                    mAdapter.updateData(listaAdapter)
                }else{
                    Toast.makeText(requireContext(), R.string.notInfoCache, Toast.LENGTH_SHORT).show()
                }

            }
        }
    }





}