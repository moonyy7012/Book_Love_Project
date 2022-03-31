package com.moon.booklove_android.data.dto

import com.moon.booklove_android.data.model.BookItem

data class BookCategory(
    var id: Int,
    var header: String,
    var books: List<BookListInfoResDTO>
)