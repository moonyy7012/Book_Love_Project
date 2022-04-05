package com.moon.booklove_android.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.moon.booklove_android.R
import com.moon.booklove_android.config.ApplicationClass.Companion.currentuser
import com.moon.booklove_android.data.dto.BookListInfoResDTO
import com.moon.booklove_android.databinding.ItemHomeBannerBinding
import com.moon.booklove_android.view.detail.DetailActivity

class HomeBannerAdapter : ListAdapter<BookListInfoResDTO, CustomViewHolder>(Companion) {

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
        val binding = ItemHomeBannerBinding.inflate(inflater, parent, false)

        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val bookListInfoResDTO = getItem(position)
        val itemBinding = holder.binding as ItemHomeBannerBinding

        when(position) {
            0 -> displayFirstBanner(itemBinding, bookListInfoResDTO)
            1 -> displaySecondBanner(itemBinding, bookListInfoResDTO)
            2 -> displayThirdBanner(itemBinding, bookListInfoResDTO)
            else -> displayFirstBanner(itemBinding, bookListInfoResDTO)
        }

        itemBinding.executePendingBindings()
        holder.itemView.apply{
            setOnClickListener{
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("bookId", bookListInfoResDTO.bookId)
                ContextCompat.startActivity(context, intent, null)
            }
        }
    }

    fun displayFirstBanner(itemBinding :ItemHomeBannerBinding, bookListInfoResDTO :BookListInfoResDTO){
        itemBinding.header.text = "요즘 이 책"
        itemBinding.itemBanner.setBackgroundResource(R.color.light_purple)
        displayBannerBinding(itemBinding, bookListInfoResDTO)
    }

    fun displaySecondBanner(itemBinding :ItemHomeBannerBinding, bookListInfoResDTO :BookListInfoResDTO){
        itemBinding.header.text = "금주의 신작"
        itemBinding.itemBanner.setBackgroundResource(R.color.pink)
        displayBannerBinding(itemBinding, bookListInfoResDTO)
    }

    fun displayThirdBanner(itemBinding :ItemHomeBannerBinding, bookListInfoResDTO :BookListInfoResDTO){
        var genderText = when(currentuser.gender){
            "Man" -> "남자"
            "Woman" -> "여자"
            else -> ""
        }
        itemBinding.header.text = "${currentuser.age}대 ${genderText}들의 선택"
        itemBinding.itemBanner.setBackgroundResource(R.color.orange)
        displayBannerBinding(itemBinding, bookListInfoResDTO)
    }

    fun displayBannerBinding(itemBinding :ItemHomeBannerBinding, bookInfoResDTO :BookListInfoResDTO){
        itemBinding.apply{
            bookTitle.text = bookInfoResDTO.title
            if(bookInfoResDTO.cover!=null){
                Glide.with(itemBinding.imageView.context)
                    .load(bookInfoResDTO.cover)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .apply(RequestOptions().fitCenter())
                    .into(itemBinding.imageView)
            }
        }
    }
}