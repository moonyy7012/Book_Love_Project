package com.moon.booklove_android.dto

data class NormalLoginResDTO(
    var userId: Long,
    var nickname: String,
    var age: String,
    var gender: String,
    var checked: Boolean,
    var userCategoryList: ArrayList<String>,
    var accessToken: String,
    var refreshToken: String
)