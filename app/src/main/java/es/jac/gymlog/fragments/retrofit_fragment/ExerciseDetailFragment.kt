package es.jac.gymlog.fragments.retrofit_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import es.jac.gymlog.R
import es.jac.gymlog.databinding.FragmentExerciseDetailBinding
import es.jac.gymlog.models.SharedViewModel

class ExerciseDetailFragment : Fragment() {

    private lateinit var binding: FragmentExerciseDetailBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExerciseDetailBinding.inflate(inflater,container,false)


        binding.tvNameExercise.text = sharedViewModel.exercise_name
        binding.tvMuscle.text = getString(R.string.exercise_muscle) + sharedViewModel.exercise_muscle
        binding.tvType.text = getString(R.string.exercise_type) + sharedViewModel.exercise_type
        binding.tvDificultad.text = getString(R.string.exercise_dificultad) + sharedViewModel.exercise_difficulty
        binding.tvEquipment.text = getString(R.string.exercise_equipment) + sharedViewModel.exercise_equipment
        binding.tvInstructions.text = getString(R.string.exercise_instructions) + sharedViewModel.exercise_instructions

        return binding.root
    }


}