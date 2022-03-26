package com.moon.booklove_android.dto

data class SingleResult<T>(
    var data: T,
    var msg: String?,
    var output: Int
)
