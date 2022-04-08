package com.moon.booklove_android.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moon.booklove_android.databinding.FavoriteItemBookBinding
import com.moon.booklove_android.data.dto.BookRecentListInfoResDTO
import com.moon.booklove_android.view.detail.DetailActivity

// ViewHolder
class RecentViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

class RecentBookAdapter : ListAdapter<BookRecentListInfoResDTO, RecentViewHolder>(Companion) {
    companion object : DiffUtil.ItemCallback<BookRecentListInfoResDTO>() {
        override fun areItemsTheSame(oldItem: BookRecentListInfoResDTO, newItem: BookRecentListInfoResDTO): Boolean {
            return  oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: BookRecentListInfoResDTO, newItem: BookRecentListInfoResDTO): Boolean {
            return  oldItem.bookId == newItem.bookId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FavoriteItemBookBinding.inflate(inflater, parent, false)

        return RecentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        val currentBook = getItem(position)
        val itemBinding = holder.binding as FavoriteItemBookBinding
        itemBinding.bookRecentListInfoResDTO = currentBook
        itemBinding.executePendingBindings()

        holder.itemView.apply{
            setOnClickListener{
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("bookId", currentBook.bookId)
                ContextCompat.startActivity(context, intent, null)
            }
        }
    }
}