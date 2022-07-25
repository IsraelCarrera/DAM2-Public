package com.example.roomcrudmarvel.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roomcrudmarvel.R
import com.example.roomcrudmarvel.databinding.PersonajesItemBinding
import com.example.roomcrudmarvel.domain.Personaje
import com.example.roomcrudmarvel.ui.infoPersonaje.VerInfoPersonaje

class PersonajeAdapter(private val delete: (Personaje) -> Unit) :
    ListAdapter<Personaje, PersonajeAdapter.PersonajeViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonajeViewHolder {
        return PersonajeViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.personajes_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PersonajeViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item, delete)
    }

    class PersonajeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = PersonajesItemBinding.bind(itemView)
        fun bind(pers: Personaje, delete: (Personaje) -> Unit) = with(binding) {
            tvNombrePersonajeRV.text = pers.nombre

            btIrInfoRV.setOnClickListener {
                val intent = Intent(it.context, VerInfoPersonaje::class.java)
                val bundle = Bundle()
                bundle.putParcelable(it.context.getString(R.string.personaje), pers)
                intent.putExtras(bundle)
                it.context.startActivity(intent)
            }
            brDeleteRV.setOnClickListener {
                delete(pers)
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Personaje>() {
    override fun areItemsTheSame(oldItem: Personaje, newItem: Personaje): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Personaje, newItem: Personaje): Boolean {
        return oldItem == newItem
    }
}