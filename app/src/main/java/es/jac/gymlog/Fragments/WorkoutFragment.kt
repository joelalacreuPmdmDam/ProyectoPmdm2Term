package es.jac.gymlog.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import es.jac.gymlog.R
import es.jac.gymlog.databinding.FragmentWorkoutBinding


class WorkoutFragment : Fragment(),NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: FragmentWorkoutBinding
    private lateinit var mListener: OnBtnBottomBarClicked


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnBtnBottomBarClicked){
            mListener = context
        }else{
            throw Exception("The activity must implement thee interface OnBtnBottomBarClicked")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkoutBinding.inflate(inflater,container,false)
        binding.bottomNavigation.setOnItemSelectedListener(this)

        return binding.root
    }



    override fun onNavigationItemSelected(item:MenuItem) = when(item.itemId){
        R.id.rm_calculatorBtn ->{
            mListener.itemCalculadoraRmClicked()
            true
        }
        R.id.personal_information ->{
            mListener.itemPersonalInfoClicked()
            true
        }
        R.id.timer ->{
            mListener.itemTimerClicked()
            true
        }
        else -> false
    }


    interface OnBtnBottomBarClicked{
        fun itemCalculadoraRmClicked()
        fun itemTimerClicked()
        fun itemPersonalInfoClicked()
    }





}