package com.moon.booklove_android.dto

data class BookListMainResDTO(
    var bookCategoryList: ArrayList<BookItem>,
    var bookInfoList: ArrayList<BookItem>,
    var bookPrivateList: ArrayList<BookItem>,
    var bookSimilarList: ArrayList<BookItem>,
    var bookBestList: ArrayList<BookItem>,
    var bookNewList: ArrayList<BookItem>

)
