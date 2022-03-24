package com.moon.booklove_android.dto

//V
data class NormalLoginResDTO(
    var id: Long,
    var nickname: String,
    var ageRange: String,
    var gender: String,
    var genre: String,
    var jwtAccess: String,
    var jwtRefresh: String
)
