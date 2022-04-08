package com.moon.booklove_android.data.model

data class BookDetailItem(
    var bookID : Long,
    var bookTitle: String,
    var bookCover: String,
    var bookDescription: String,
    var bookAuthor: String,
    var bookPublishDate: String,
    var bookPublisher: String,
    var bookISBN: String,
    var bookPrice: Int,
    var bookSalePrice: Int,
    var bookRating: Int,
    var bookLink: String,
    var bookCategory: String,
    var bookSalesPoint: Int,
    var bookSimilarList: ArrayList<BookItem>
)