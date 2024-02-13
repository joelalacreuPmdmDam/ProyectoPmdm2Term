package es.jac.gymlog.models

import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {

    var exercise_name : String = ""
    var exercise_muscle : String = ""
    var exercise_type : String = ""
    var exercise_difficulty : String = ""
    var exercise_equipment : String = ""
    var exercise_instructions : String = ""
}