package com.moon.booklove_android.adapter

import android.content.ClipData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moon.booklove_android.databinding.ItemBookCategoryBinding
import com.moon.booklove_android.data.dto.BookCategory
import com.moon.booklove_android.data.dto.BookRecomm
import com.moon.booklove_android.databinding.ItemBookRecommBinding

class BookRecommAdapter : ListAdapter<BookRecomm, CustomViewHolder>(Companion) {
    private val viewPool = RecyclerView.RecycledViewPool()

    companion object : DiffUtil.ItemCallback<BookRecomm>() {
        override fun areItemsTheSame(oldItem: BookRecomm, newItem: BookRecomm): Boolean {
            return  oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: BookRecomm, newItem: BookRecomm): Boolean {
            return  oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBookRecommBinding.inflate(inflater, parent, false)

        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentBookRecomm = getItem(position)
        val itemBinding = holder.binding as ItemBookRecommBinding

        itemBinding.bookRecomm = currentBookRecomm
        itemBinding.nestedRecyclerView.setRecycledViewPool(viewPool)
        itemBinding.executePendingBindings()
    }
}