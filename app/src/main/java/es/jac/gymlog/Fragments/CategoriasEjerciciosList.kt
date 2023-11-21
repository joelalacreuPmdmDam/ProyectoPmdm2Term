package es.jac.gymlog.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import es.jac.gymlog.Adapters.CategoriaEjerciciosAdapter
import es.jac.gymlog.Classes.CategoriaEjercicio
import es.jac.gymlog.R
import es.jac.gymlog.databinding.FragmentCategoriasEjerciciosListBinding


class CategoriasEjerciciosList : Fragment() {

    private lateinit var binding: FragmentCategoriasEjerciciosListBinding
    private lateinit var mListener: CategoriasListListener


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is CategoriasListListener){
            mListener = context
        }else{
            throw Exception("The activity must implement thee interface CategoriasListListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriasEjerciciosListBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }



    private fun setUpRecyclerView() {
        val listaCategorias = mutableListOf<CategoriaEjercicio>(
            CategoriaEjercicio(1,"Chest"),
            CategoriaEjercicio(2,"Back"),
            CategoriaEjercicio(3,"Legs")
        )

        val clickItemFunction = {categoria: CategoriaEjercicio ->
            Toast.makeText(requireContext(),"Clicked category is: ${categoria.nombreCategoria}",Toast.LENGTH_SHORT).show()
            mListener.onItemClick(categoria)
        }


        val categoriaEjerciciosAdapter = CategoriaEjerciciosAdapter(listaCategorias,requireContext(),clickItemFunction)
        binding.recViewCategoriasEjercicios.addItemDecoration(
            DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL)
        )
        binding.recViewCategoriasEjercicios.adapter = categoriaEjerciciosAdapter
        binding.recViewCategoriasEjercicios.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
    }


    interface CategoriasListListener{
        fun onItemClick(categoria: CategoriaEjercicio)
    }


}