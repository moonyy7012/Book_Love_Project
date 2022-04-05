package com.moon.booklove_android.config

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.moon.booklove_android.adapter.BookItemAdapter
import com.moon.booklove_android.config.ApplicationClass.Companion.bookCategoryAdapter
import com.moon.booklove_android.data.dto.BookListInfoResDTO

@BindingAdapter(value = ["setBooks"])
fun RecyclerView.setBooks(books: List<BookListInfoResDTO>?) {
    if (books != null) {
        bookCategoryAdapter = BookItemAdapter()
        bookCategoryAdapter!!.submitList(books)
        adapter = bookCategoryAdapter
    }
}

@BindingAdapter(value = ["setBookCover"])
fun setBookCover(imageView: ImageView, url: String?){

    if(url!=null){
        Glide.with(imageView.context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .apply(RequestOptions().fitCenter())
            .into(imageView)
    }
}

@BindingAdapter(value = ["setRecommBooks"])
fun RecyclerView.setRecommBooks(books: List<BookListInfoResDTO>?) {
    if (books != null) {
        val bookAdapter = BookItemAdapter()
        bookAdapter.submitList(books)
        adapter = bookAdapter
    }
}