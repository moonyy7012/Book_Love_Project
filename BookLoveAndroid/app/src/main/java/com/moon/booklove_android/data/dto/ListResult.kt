package com.moon.booklove_android.data.dto

data class ListResult<T>(
    var data: List<T>,
    var msg: String?,
    var output: Int
)
