package com.moon.booklove_android.dto

data class BookListSearchResDTO(
    var bookCategoryList: ArrayList<BookItem>,
    var bookTitleList: ArrayList<BookItem>,
    var bookAuthorList: ArrayList<BookItem>
)
