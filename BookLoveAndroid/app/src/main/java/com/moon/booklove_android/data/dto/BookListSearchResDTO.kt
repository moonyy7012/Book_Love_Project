package com.moon.booklove_android.data.dto

import com.moon.booklove_android.data.model.BookItem

data class BookListSearchResDTO(
    var bookCategoryList: ArrayList<BookItem>,
    var bookTitleList: ArrayList<BookItem>,
    var bookAuthorList: ArrayList<BookItem>
)
