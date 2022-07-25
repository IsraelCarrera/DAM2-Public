package com.example.pelisyseriesapp.ui.buscaDetalladaSerie

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
import com.example.pelisyseriesapp.databinding.ItemTemporadaBinding
import com.example.pelisyseriesapp.domain.Temporada

class TemporadasAdapter(
    val context: Context,
    val actions: GenericAction
) : ListAdapter<Temporada, TemporadasAdapter.ItemViewholder>(DiffCallBack()) {
    interface GenericAction {
        fun verMas(temporada: Temporada)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_temporada, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }

    inner class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemTemporadaBinding.bind(itemView)
        fun bind(item: Temporada) {
            with(binding) {
                tvNombreTemporada.text = item.nombre
                tvNumEpisodios.text = item.numEpisodios.toString()
                tvNumTemporada.text = item.numeroTemporada.toString()
                tvSipnosis.text = item.sipnopsis
                ivPostTemporada.load(item.poster)
            }
            itemView.setOnClickListener {
                actions.verMas(item)
            }
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<Temporada>() {
        override fun areItemsTheSame(oldItem: Temporada, newItem: Temporada): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Temporada, newItem: Temporada): Boolean {
            return oldItem == newItem
        }
    }
}