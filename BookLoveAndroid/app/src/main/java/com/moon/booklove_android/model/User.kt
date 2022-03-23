package com.moon.booklove_android.model

data class User(
    var id: Long,
    var userid: String,
    var password: String,
    var logintype: String,
    var nickname: String,
    var agerange: String,
    var gender: String
)
