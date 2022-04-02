package com.moon.booklove_android.config

import android.content.Context
import android.widget.Toast
import com.moon.booklove_android.R
import com.moon.booklove_android.config.ApplicationClass.Companion.recommand
import com.moon.booklove_android.data.dto.*

fun getRecomm(bookMainListResDTO :BookMainListResDTO): List<BookRecomm> {
    val bookList = arrayListOf<BookRecomm>()
    bookList+=BookRecomm(0, recommand[0], bookMainListResDTO.bookGenderAgeList)
    bookList+=BookRecomm(1, recommand[1], bookMainListResDTO.bookCategoryList)
    bookList+=BookRecomm(2, recommand[2], bookMainListResDTO.bookBestSellerList)
    bookList+=BookRecomm(3, recommand[3], bookMainListResDTO.bookNewList)
    bookList+=BookRecomm(4, recommand[4], bookMainListResDTO.bookRecentSimilarList)


    return bookList
}



fun getGenre(): ArrayList<Book> {
    val books = arrayListOf<Book>()
    for (a in 0..30) {
        books += Book(a, "Book Title $a", R.drawable.ic_baseline_book_24, "재밌는 책입니다.")
    }

    return books
}

fun getAuthorBooks(): ArrayList<Book> {
    val books = arrayListOf<Book>()
    for (a in 0..30) {
        books += Book(a, "Book Title $a", R.drawable.ic_baseline_book_24, "재밌는 책입니다.")
    }

    return books
}

fun toast(text: String, context:Context) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}