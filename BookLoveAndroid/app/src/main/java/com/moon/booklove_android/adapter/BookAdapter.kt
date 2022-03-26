package com.moon.booklove_android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moon.booklove_android.databinding.ItemBookBinding
import com.moon.booklove_android.dto.Book

// ViewHolder
class CustomViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)


class BookAdapter : ListAdapter<Book, CustomViewHolder>(Companion) {
    companion object : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return  oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return  oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBookBinding.inflate(inflater, parent, false)

        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentBook = getItem(position)
        val itemBinding = holder.binding as ItemBookBinding
        itemBinding.book = currentBook
        itemBinding.executePendingBindings()
    }
}
