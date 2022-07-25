package com.example.pelisyseriesapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pelisyseriesapp.R
import com.example.pelisyseriesapp.databinding.ItemActoresActuanBinding
import com.example.pelisyseriesapp.domain.ActoresActuan

class ActoresActuanAdapter(
    val context: Context,
) : ListAdapter<ActoresActuan, ActoresActuanAdapter.ItemViewholder>(DiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_actores_actuan, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }

    inner class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemActoresActuanBinding.bind(itemView)
        fun bind(item: ActoresActuan) {
            with(binding) {
                ivFotoActor.load(item.foto)
                tvPonerGenero.text = item.genero
                tvPonerNombre.text = item.nombreReal
                tvPonerPersonaje.text = item.nombrePersonaje
            }
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<ActoresActuan>() {
        override fun areItemsTheSame(oldItem: ActoresActuan, newItem: ActoresActuan): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ActoresActuan, newItem: ActoresActuan): Boolean {
            return oldItem == newItem
        }
    }
}