package com.moon.booklove_android.data.dto

data class BookInfoResDTO(
    var bookId : Long,
    var title: String,
    var description: String,
    var author: String,
    var pubDate: String,
    var isbn: String,
    var priceSales: Int,
    var priceStandard: Int,
    var cover: String?,
    var link: String,
    var categoryName: String,
    var publisher: String,
    var salesPoint: Int,
    var customerReviewRank: Int,
    var similarBooks: List<BookListInfoResDTO>
)