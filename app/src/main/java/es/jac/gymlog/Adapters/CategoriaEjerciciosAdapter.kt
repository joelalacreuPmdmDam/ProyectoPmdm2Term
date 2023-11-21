package es.jac.gymlog.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.jac.gymlog.Classes.CategoriaEjercicio
import es.jac.gymlog.Fragments.CategoriasEjerciciosList
import es.jac.gymlog.R

class CategoriaEjerciciosAdapter(private val listaCategorias: MutableList<CategoriaEjercicio>,
                                 private val context: Context,
                                 private val mListener: (CategoriaEjercicio)-> Unit):
    RecyclerView.Adapter<CategoriaEjerciciosAdapter.CategoriaEjerciciosViewHolder>(){




    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriaEjerciciosViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.categoria_item,parent,false)
        return CategoriaEjerciciosViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaCategorias.size
    }

    override fun onBindViewHolder(holder: CategoriaEjerciciosViewHolder, position: Int) {
        val categoria = listaCategorias[position]
        holder.bindItem(categoria)
        holder.itemView.setOnClickListener{
            mListener(categoria)
        }
    }

    class CategoriaEjerciciosViewHolder(view: View): RecyclerView.ViewHolder(view){

        val tv_nombreCategoria: TextView = view.findViewById(R.id.tv_nombreCategoria)

        fun bindItem(categoria: CategoriaEjercicio){
            tv_nombreCategoria.text = categoria.nombreCategoria
        }

    }
}