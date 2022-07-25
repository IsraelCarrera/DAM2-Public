package com.example.marvelcaracters.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcaracters.R
import com.example.marvelcaracters.databinding.ComicsItemBinding

class ComicAdapter(private val comics: List<String>) : RecyclerView.Adapter<ComicsViewHolder>() {

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
    fun render(comics: String) {
        binding.tvComicsRV.text = comics
    }
}