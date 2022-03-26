package com.moon.booklove_android.dto

//V, Req는 없음
data class SocialLoginResDTO(
    var id: Long,
    var nickname: String,
    var ageRange: String,
    var gender: String,
    var genre: String,
    var jwtAccess: String,
    var jwtRefresh: String
)
