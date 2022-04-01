package com.moon.booklove_android.adapter

import android.content.ClipData
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.moon.booklove_android.R
import com.moon.booklove_android.config.ApplicationClass.Companion.currentuser
import com.moon.booklove_android.databinding.ItemBookCategoryBinding
import com.moon.booklove_android.data.dto.BookCategory
import com.moon.booklove_android.data.dto.BookInfoResDTO
import com.moon.booklove_android.data.dto.BookRecomm
import com.moon.booklove_android.databinding.ItemBookRecommBinding
import com.moon.booklove_android.databinding.ItemHomeBannerBinding
import com.moon.booklove_android.view.detail.DetailActivity

class HomeBannerAdapter : ListAdapter<BookInfoResDTO, CustomViewHolder>(Companion) {

    companion object : DiffUtil.ItemCallback<BookInfoResDTO>() {
        override fun areItemsTheSame(oldItem: BookInfoResDTO, newItem: BookInfoResDTO): Boolean {
            return  oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: BookInfoResDTO, newItem: BookInfoResDTO): Boolean {
            return  oldItem.bookId == newItem.bookId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomeBannerBinding.inflate(inflater, parent, false)

        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val bookInfoResDTO = getItem(position)
        val itemBinding = holder.binding as ItemHomeBannerBinding
        when(position) {
            0 -> displayFirstBanner(itemBinding, bookInfoResDTO)
            1 -> displaySecondBanner(itemBinding, bookInfoResDTO)
            2 -> displayThirdBanner(itemBinding, bookInfoResDTO)
            else -> displayFirstBanner(itemBinding, bookInfoResDTO)
        }
        itemBinding.executePendingBindings()
        holder.itemView.apply{
            setOnClickListener{
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("bookId", bookInfoResDTO.bookId)
                ContextCompat.startActivity(context, intent, null)
            }
        }

    }
    fun displayFirstBanner(itemBinding :ItemHomeBannerBinding, bookInfoResDTO :BookInfoResDTO){
        itemBinding.header.text = "요즘 이 책"
        itemBinding.itemBanner.setBackgroundResource(R.color.light_purple)
        displayBannerBinding(itemBinding, bookInfoResDTO)
    }

    fun displaySecondBanner(itemBinding :ItemHomeBannerBinding, bookInfoResDTO :BookInfoResDTO){
        itemBinding.header.text = "${bookInfoResDTO.author} 신작"
        itemBinding.itemBanner.setBackgroundResource(R.color.orange)
        displayBannerBinding(itemBinding, bookInfoResDTO)
    }

    fun displayThirdBanner(itemBinding :ItemHomeBannerBinding, bookInfoResDTO :BookInfoResDTO){
        var genderText = when(currentuser.gender){
            "Man" -> "남자"
            "Woman" -> "여자"
            else -> ""
        }
        itemBinding.header.text = "${currentuser.age}대 ${genderText}들의 선택"
        itemBinding.itemBanner.setBackgroundResource(R.color.pink)
        displayBannerBinding(itemBinding, bookInfoResDTO)
    }

    fun displayBannerBinding(itemBinding :ItemHomeBannerBinding, bookInfoResDTO :BookInfoResDTO){
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