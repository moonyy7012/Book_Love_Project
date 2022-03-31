package com.moon.booklove_android.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.moon.booklove_android.data.dto.BookListInfoResDTO
import com.moon.booklove_android.databinding.ItemBookitemBinding
import com.moon.booklove_android.view.detail.DetailActivity

// ViewHolder
class CustomViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

class BookItemAdapter : ListAdapter<BookListInfoResDTO, CustomViewHolder>(Companion) {

    companion object : DiffUtil.ItemCallback<BookListInfoResDTO>() {
        override fun areItemsTheSame(oldItem: BookListInfoResDTO, newItem: BookListInfoResDTO): Boolean {
            return  oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: BookListInfoResDTO, newItem: BookListInfoResDTO): Boolean {
            return  oldItem.bookId == newItem.bookId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBookitemBinding.inflate(inflater, parent, false)

        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentBook = getItem(position)
        val itemBinding = holder.binding as ItemBookitemBinding
        itemBinding.book = currentBook
        Glide.with(holder.itemView.context).load(currentBook.cover).into(itemBinding.imageView)

        itemBinding.executePendingBindings()
        holder.itemView.apply{
            setOnClickListener{
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("bookId", currentBook.bookId)
                startActivity(context, intent, null)
            }
        }
    }
}
