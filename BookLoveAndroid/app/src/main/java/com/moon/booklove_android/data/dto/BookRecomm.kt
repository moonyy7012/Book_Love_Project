package com.moon.booklove_android.data.dto

data class BookRecomm(
    var id: Int,
    var header: String,
    var books: List<BookListInfoResDTO>
)