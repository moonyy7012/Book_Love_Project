package com.moon.booklove_android.data.dto

data class SingleResult<T>(
    var data: T,
    var msg: String?,
    var output: Int
)
