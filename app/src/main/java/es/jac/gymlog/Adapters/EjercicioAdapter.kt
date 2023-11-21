package es.jac.gymlog.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.jac.gymlog.Classes.Ejercicio
import es.jac.gymlog.R

class EjercicioAdapter(private val listaEjercicios: MutableList<Ejercicio>,
                        private val context: Context):
                        RecyclerView.Adapter<EjercicioAdapter.EjerciciosViewHolder>(){




    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EjerciciosViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.ejercicio_item,parent,false)
        return EjerciciosViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaEjercicios.size
    }

    override fun onBindViewHolder(holder: EjerciciosViewHolder, position: Int) {
        val ejercicio = listaEjercicios[position]
        holder.bindItem(ejercicio)
    }


    class EjerciciosViewHolder(view: View): RecyclerView.ViewHolder(view){

        val tv_nombreEjercicio: TextView = view.findViewById(R.id.tv_nombreEjercicio)

        fun bindItem(ejercicio: Ejercicio){
            tv_nombreEjercicio.text = ejercicio.nombreEjercicio
        }
    }
}