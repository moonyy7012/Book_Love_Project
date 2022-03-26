package com.moon.booklove_android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moon.booklove_android.databinding.FavoriteItemBookBinding
import com.moon.booklove_android.dto.Book

// ViewHolder
class FavoriteViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)


class FavoriteBookAdapter : ListAdapter<Book, FavoriteViewHolder>(Companion) {
    companion object : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return  oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return  oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FavoriteItemBookBinding.inflate(inflater, parent, false)

        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val currentBook = getItem(position)
        val itemBinding = holder.binding as FavoriteItemBookBinding
        itemBinding.book = currentBook
        itemBinding.executePendingBindings()
    }
}
