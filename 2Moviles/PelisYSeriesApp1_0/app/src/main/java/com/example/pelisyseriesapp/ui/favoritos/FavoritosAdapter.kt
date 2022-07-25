package com.example.pelisyseriesapp.ui.favoritos

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pelisyseriesapp.R
import com.example.pelisyseriesapp.databinding.ItemFavoritosBinding
import com.example.pelisyseriesapp.domain.Favoritos
import com.example.pelisyseriesapp.ui.SwipeGesture
import com.example.pelisyseriesapp.utils.Constantes

class FavoritosAdapter(
    val context: Context,
    val actions: GenericAction
) : ListAdapter<Favoritos, FavoritosAdapter.ItemViewholder>(DiffCallBack()) {

    interface GenericAction {
        fun verMas(favoritos: Favoritos)
        fun deleteItem(favoritos: Favoritos)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_favoritos, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }

    inner class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemFavoritosBinding.bind(itemView)
        fun bind(item: Favoritos) {
            with(binding) {
                //Añadir info
                tvNombreObjeto.text = item.titulo
                ivPortada.load(item.poster)
                if (item.tipo == Constantes.PELICULA) {
                    tvQueEs.text = Constantes.PELICULA
                    tvQueEs.setTextColor(context.resources.getColor(R.color.peli))
                } else {
                    tvQueEs.text = Constantes.SERIE
                    tvQueEs.setTextColor(context.resources.getColor(R.color.serie))
                }
                //Click, para ir a ver.
                itemView.setOnClickListener {
                    actions.verMas(item)
                }
            }
        }
    }

    //PARA ELIMINAR EN EL ADAPTER FAVORITOS
    val swipeGesture = object : SwipeGesture(context) {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            when (direction) {
                ItemTouchHelper.RIGHT -> {
                    actions.deleteItem(currentList[viewHolder.adapterPosition])
                }
            }
        }
    }


    class DiffCallBack : DiffUtil.ItemCallback<Favoritos>() {
        override fun areItemsTheSame(oldItem: Favoritos, newItem: Favoritos): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Favoritos, newItem: Favoritos): Boolean {
            return oldItem == newItem
        }
    }
}