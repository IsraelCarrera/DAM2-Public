package com.example.pelisyseriesapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pelisyseriesapp.R
import com.example.pelisyseriesapp.databinding.ItemGenericoBinding
import com.example.pelisyseriesapp.domain.GenericoSeriesPelis


class GenericoAdapter(
    val context: Context,
    val actions: GenericAction
) : ListAdapter<GenericoSeriesPelis, GenericoAdapter.ItemViewholder>(DiffCallBack()) {

    interface GenericAction {
        fun verMas(id: Int)
        fun empezandoSelectMode()
        fun itemHasClicked(genericoSeriesPelis: GenericoSeriesPelis)
        fun isItemSelected(genericoSeriesPelis: GenericoSeriesPelis): Boolean
    }

    //Para saber si estamos en seleccion
    private var selectedMode: Boolean = false

    fun salirDelSelectedMode() {
        selectedMode = false
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_generico, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }

    inner class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemGenericoBinding.bind(itemView)
        fun bind(item: GenericoSeriesPelis) {
            //Click largo, para multiselección y con ello, añadir o añadir varias.
            itemView.setOnLongClickListener {
                if (!selectedMode) {
                    selectedMode = true
                    actions.empezandoSelectMode()
                    //Si es favorito, no debería de ser seleccionado.
                    if (!item.esFavorito) {
                        binding.cbSeleccionar.isChecked = true
                        itemView.setBackgroundColor(Color.GREEN)
                    }
                    notifyDataSetChanged()
                }
                true
            }
            with(binding) {
                //Vamos con el multiselect. Si está en favoritos ya, NO puede ser seleccionado.
                cbSeleccionar.setOnClickListener {
                    if (selectedMode)
                        if (cbSeleccionar.isChecked) {
                            itemView.setBackgroundColor(Color.GREEN)
                        } else {
                            itemView.setBackgroundColor(Color.WHITE)
                        }
                    actions.itemHasClicked(item)
                }


                //Si esta el modo seleccionar, es visible el CB.
                if (selectedMode) {
                    cbSeleccionar.visibility = View.VISIBLE
                } else {
                    cbSeleccionar.visibility = View.GONE
                }
                //Si esta en la lista de seleccionados, aparecerá de un color.
                if (actions.isItemSelected(item)) {
                    itemView.setBackgroundColor(Color.GREEN)
                    cbSeleccionar.isChecked = true
                } else {
                    itemView.setBackgroundColor(Color.WHITE)
                    cbSeleccionar.isChecked = false
                }
                //Añadir info
                tvNombrePelicula.text = item.titulo
                ivPortada.load(item.poster)
                //Click, para ir a ver.
                itemView.setOnClickListener {
                    if (!selectedMode) {
                        actions.verMas(item.id)
                    }
                }
            }
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<GenericoSeriesPelis>() {
        override fun areItemsTheSame(
            oldItem: GenericoSeriesPelis,
            newItem: GenericoSeriesPelis
        ): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: GenericoSeriesPelis,
            newItem: GenericoSeriesPelis
        ): Boolean {
            return oldItem == newItem
        }
    }
}