package com.moon.booklove_android.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moon.booklove_android.view.detail.DetailActivity
import com.moon.booklove_android.data.dto.BookListInfoResDTO
import com.moon.booklove_android.databinding.ItemBookBinding

// ViewHolder
class CustomViewHolder2(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

class BookAdapter : ListAdapter<BookListInfoResDTO, CustomViewHolder2>(Companion) {

    companion object : DiffUtil.ItemCallback<BookListInfoResDTO>() {
        override fun areItemsTheSame(oldItem: BookListInfoResDTO, newItem: BookListInfoResDTO): Boolean {
            return  oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: BookListInfoResDTO, newItem: BookListInfoResDTO): Boolean {
            return  oldItem.bookId == newItem.bookId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder2 {
        val inflater = LayoutInflater.from(parent.context)
        val binding = com.moon.booklove_android.databinding.ItemBookBinding.inflate(inflater, parent, false)

        return CustomViewHolder2(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder2, position: Int) {
        val currentBook = getItem(position)
        val itemBinding = holder.binding as ItemBookBinding
        itemBinding.book = currentBook
        itemBinding.executePendingBindings()
        holder.itemView.apply{
            setOnClickListener{
                val intent = Intent(context, DetailActivity::class.java)
                startActivity(context, intent, null)
            }
        }
    }
}
