package com.moon.booklove_android.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moon.booklove_android.config.ApplicationClass.Companion.bookInfoHeader
import com.moon.booklove_android.databinding.ItemBookDetailBinding


class DetailViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

class BookDetailAdapter : ListAdapter<String, DetailViewHolder>(Companion) {

    companion object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return  oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return  oldItem==newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBookDetailBinding.inflate(inflater, parent, false)

        return DetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val bookDetail = getItem(position)
        val itemBinding = holder.binding as ItemBookDetailBinding

        Log.d("리스트", "onBindViewHolder: ${bookInfoHeader[position]}, ${bookDetail}")
        itemBinding.detailTitle.text = bookInfoHeader[position]
        itemBinding.detailTextView.text = bookDetail
        itemBinding.executePendingBindings()
    }
}