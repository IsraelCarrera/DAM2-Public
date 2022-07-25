package com.example.roomcrudmarvel.ui.comics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomcrudmarvel.R
import com.example.roomcrudmarvel.databinding.ComicsItemBinding
import com.example.roomcrudmarvel.domain.Comic

class ComicAdapter(private val comics: List<Comic>) : RecyclerView.Adapter<ComicsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ComicsViewHolder(layoutInflater.inflate(R.layout.comics_item, parent, false))
    }

    override fun onBindViewHolder(holder: ComicsViewHolder, position: Int) {
        holder.render(comics[position])
    }

    override fun getItemCount(): Int {
        return comics.size
    }
}

class ComicsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    val binding = ComicsItemBinding.bind(view)
    fun render(comics: Comic) {
        binding.tvComicsRV.text = comics.nombre
    }
}