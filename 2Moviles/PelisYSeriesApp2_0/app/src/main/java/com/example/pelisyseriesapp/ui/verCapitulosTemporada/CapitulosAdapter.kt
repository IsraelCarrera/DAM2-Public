package com.example.pelisyseriesapp.ui.verCapitulosTemporada

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pelisyseriesapp.R
import com.example.pelisyseriesapp.databinding.ItemEpisodioBinding
import com.example.pelisyseriesapp.domain.Episodio
import com.example.pelisyseriesapp.utils.Constantes


class CapitulosAdapter(
    val context: Context,
    val actions: GenericAction,
) : ListAdapter<Episodio, CapitulosAdapter.ItemViewholder>(DiffCallBack()) {

    interface GenericAction {
        fun updateCapitulo(episodio: Episodio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_episodio, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }

    inner class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemEpisodioBinding.bind(itemView)
        fun bind(item: Episodio) {
            with(binding) {
                var identificador: String = if (item.numTemporada < 10) {
                    Constantes.CERO + item.numTemporada
                } else {
                    item.numTemporada.toString()
                }
                identificador += if (item.numCapitulo < 10) {
                    Constantes.CEROX + item.numCapitulo
                } else {
                    Constantes.X + item.numCapitulo
                }
                tvNombreCapitulo.text = item.nombre
                tvIdentificador.text = identificador
                if (item.haSidoVisto) {
                    swVisto.isChecked = true
                    swVisto.isEnabled = false
                } else {
                    swVisto.isChecked = false
                    swVisto.isEnabled = true
                }
                swVisto.setOnClickListener {

                    actions.updateCapitulo(item)
                    swVisto.isChecked = true
                    swVisto.isEnabled = false
                }
            }
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<Episodio>() {
        override fun areItemsTheSame(oldItem: Episodio, newItem: Episodio): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Episodio, newItem: Episodio): Boolean {
            return oldItem == newItem
        }
    }
}