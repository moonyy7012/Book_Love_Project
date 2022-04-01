package com.moon.booklove_android.config

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moon.booklove_android.adapter.BookAdapter
import com.moon.booklove_android.adapter.BookItemAdapter
import com.moon.booklove_android.data.dto.Book
import com.moon.booklove_android.data.dto.BookListInfoResDTO
import com.moon.booklove_android.data.dto.BookRecomm

@BindingAdapter(value = ["setBooks"])
fun RecyclerView.setBooks(books: List<Book>?) {
    if (books != null) {
        val bookAdapter = BookAdapter()
        bookAdapter.submitList(books)

        adapter = bookAdapter
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
