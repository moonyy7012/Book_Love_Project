package com.moon.booklove_android.data.dto


data class BookMainListResDTO(
    var bookBestSellerList : ArrayList<BookListInfoResDTO>,
    var bookNewList: ArrayList<BookListInfoResDTO>,
    var bookCategoryList: ArrayList<BookListInfoResDTO>,
    var bookGenderAgeList: ArrayList<BookListInfoResDTO>,
)