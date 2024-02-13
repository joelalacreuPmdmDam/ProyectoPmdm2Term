package es.jac.gymlog.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.google.android.material.navigation.NavigationBarView
import es.jac.gymlog.R
import es.jac.gymlog.databinding.FragmentWorkoutBinding


class WorkoutFragment : Fragment() {

    private lateinit var binding: FragmentWorkoutBinding





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkoutBinding.inflate(inflater,container,false)


        return binding.root
    }






}