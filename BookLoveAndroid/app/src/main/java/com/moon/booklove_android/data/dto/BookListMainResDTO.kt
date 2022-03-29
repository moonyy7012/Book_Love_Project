package com.moon.booklove_android.data.dto

import com.moon.booklove_android.data.model.BookItem

data class BookListMainResDTO(
    var bookCategoryList: ArrayList<BookItem>,
    var bookInfoList: ArrayList<BookItem>,
    var bookPrivateList: ArrayList<BookItem>,
    var bookSimilarList: ArrayList<BookItem>,
    var bookBestList: ArrayList<BookItem>,
    var bookNewList: ArrayList<BookItem>

)
