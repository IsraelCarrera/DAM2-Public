package com.example.roomcrudmarvel.ui.infoPersonaje


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roomcrudmarvel.R
import com.example.roomcrudmarvel.databinding.ComicsItemBinding
import com.example.roomcrudmarvel.domain.Comic


class ComicAdapter : ListAdapter<Comic, ComicAdapter.ComicsViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsViewHolder {
        return ComicsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.comics_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ComicsViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }


    class ComicsViewHolder(comicView: View) : RecyclerView.ViewHolder(comicView) {
        private val binding = ComicsItemBinding.bind(comicView)
        fun bind(comics: Comic) = with(binding) {
            tvComicsRV.text = comics.nombre
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Comic>() {
    override fun areItemsTheSame(oldItem: Comic, newItem: Comic): Boolean {
        return oldItem.idComic == newItem.idComic
    }

    override fun areContentsTheSame(oldItem: Comic, newItem: Comic): Boolean {
        return oldItem == newItem
    }
}