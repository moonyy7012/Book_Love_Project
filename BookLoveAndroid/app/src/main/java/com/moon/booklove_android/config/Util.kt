package com.moon.booklove_android.config

import android.content.Context
import android.widget.Toast
import com.moon.booklove_android.R
import com.moon.booklove_android.config.ApplicationClass.Companion.recommand
import com.moon.booklove_android.config.ApplicationClass.Companion.search
import com.moon.booklove_android.dto.Book
import com.moon.booklove_android.dto.BookCategory

fun getRecomm(): ArrayList<BookCategory> {
    val bookCategory = arrayListOf<BookCategory>()
    for (a in 0..2) {
        val bookList = arrayListOf<Book>()
        for(b in 0..5) {
            val book = Book(b, "Book Title $b", R.drawable.ic_baseline_book_24, "재밌는 책입니다.")
            bookList += book
        }

        bookCategory += BookCategory(a, recommand[a], bookList)
    }

    return bookCategory
}

fun getSearch(): ArrayList<BookCategory> {
    val bookCategory = arrayListOf<BookCategory>()
    for (a in 0..2) {
        val bookList = arrayListOf<Book>()
        for(b in 0..5) {
            val book = Book(b, "Book Title $b", R.drawable.ic_baseline_book_24, "재밌는 책입니다.")
            bookList += book
        }

        bookCategory += BookCategory(a, search[a], bookList)
    }

    return bookCategory
}

fun getGenre(): ArrayList<Book> {
    val books = arrayListOf<Book>()
    for (a in 0..30) {
        books += Book(a, "Book Title $a", R.drawable.ic_baseline_book_24, "재밌는 책입니다.")
    }

    return books
}

fun toast(text: String, context:Context) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}