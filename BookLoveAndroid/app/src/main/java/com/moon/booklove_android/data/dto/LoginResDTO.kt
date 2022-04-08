package com.moon.booklove_android.data.dto

data class LoginResDTO(
    var id: Long,
    var nickname: String,
    var age: Int,
    var gender: String,
    var checked: Boolean,
    var type: String,
    var userId: String,
    var userCategoryList: ArrayList<String>,
    var accessToken: String,
    var refreshToken: String
)