package es.jac.gymlog.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.jac.gymlog.R
import es.jac.gymlog.models.ExerciseResponse

class ExercisesAdapter(private val ejercicios: MutableList<ExerciseResponse>,
                       private val mListener: (ExerciseResponse) -> Unit,
                       private val mInfoListener: (ExerciseResponse) -> Unit):
    RecyclerView.Adapter<ExercisesAdapter.ExercisesViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ExercisesViewHolder(layoutInflater.inflate(R.layout.exercise_item, parent, false))
    }

    override fun getItemCount(): Int = ejercicios.size

    override fun onBindViewHolder(holder: ExercisesViewHolder, position: Int) {
        val item = ejercicios[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { mListener(item) }
        holder.itemView.findViewById<ImageButton>(R.id.btn_info_exercise).setOnClickListener { mInfoListener(item) }

    }

    fun updateData(newExercicesList: List<ExerciseResponse>) {
        ejercicios.clear()
        ejercicios.addAll(newExercicesList)
        notifyDataSetChanged()
    }

    class ExercisesViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tv_nombreEjer = view.findViewById<TextView>(R.id.tv_nombre_ejercicio)
        fun bind(item: ExerciseResponse){
            tv_nombreEjer.text = item.name
        }
    }
}