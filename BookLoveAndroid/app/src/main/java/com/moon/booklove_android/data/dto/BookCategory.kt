package com.moon.booklove_android.data.dto

data class BookCategory(
    var id: Int,
    var header: String,
    var books: MutableList<Book>
)