package es.jac.gymlog.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import es.jac.gymlog.R
import es.jac.gymlog.databinding.FragmentTimerBinding
import es.jac.gymlog.managers.TimerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TimerFragment : Fragment() {


    private lateinit var binding: FragmentTimerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimerBinding.inflate(inflater,container,false)
        binding.btnStartTimer?.setOnClickListener{
            val numSegundos: Int = binding.etTimer?.text.toString().toInt()
            lifecycleScope.launch(Dispatchers.IO) {
                TimerRepository().getCount(numSegundos).collect{ number ->

                    withContext(Dispatchers.Main) {
                        binding.etTimer?.setText(number.toString())
                    }
                }
            }
        }
        return binding.root
    }

}