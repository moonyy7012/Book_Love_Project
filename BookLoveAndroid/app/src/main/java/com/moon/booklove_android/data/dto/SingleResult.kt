package com.moon.booklove_android.data.dto

import com.google.gson.annotations.SerializedName

data class SingleResult<T>(
    var data: T,
    var msg: String?,
    var output: Int
)
