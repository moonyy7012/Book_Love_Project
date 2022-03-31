package com.moon.booklove_android.config

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.moon.booklove_android.adapter.BookAdapter
import com.moon.booklove_android.adapter.BookItemAdapter
import com.moon.booklove_android.config.ApplicationClass.Companion.bookCategoryAdapter
import com.moon.booklove_android.data.dto.Book
import com.moon.booklove_android.data.dto.BookListInfoResDTO
import com.moon.booklove_android.data.model.BookItem

//@BindingAdapter(value = ["setBooks"])
//fun RecyclerView.setBooks(books: List<BookListInfoResDTO>?) {
//    if (books != null) {
//        val bookAdapter = BookAdapter()
//        bookAdapter.submitList(books)
//
//        adapter = bookAdapter
//    }
//}
@BindingAdapter(value = ["setBooks"])
fun RecyclerView.setBooks(books: List<BookListInfoResDTO>?) {
    if (books != null) {
        bookCategoryAdapter = BookItemAdapter()
        bookCategoryAdapter!!.submitList(books)

        adapter = bookCategoryAdapter
    }
}



@BindingAdapter(value = ["setBookCover"])
fun setBookCover(imageView: ImageView, url: String){
        Glide.with(imageView.context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .apply(RequestOptions().fitCenter())
            .into(imageView)

}
//@BindingAdapter(value = ["setBookCover"])
//fun RecyclerView.setBookCover(imageView: ImageView, url: String, placeholder: Drawable){
//    Glide.with(imageView.context)
//        .load(url)
//        .placeholder(placeholder)
//        .error(placeholder)
//        .diskCacheStrategy(DiskCacheStrategy.NONE)
//        .apply(RequestOptions().fitCenter())
//        .into(imageView)
//
//}