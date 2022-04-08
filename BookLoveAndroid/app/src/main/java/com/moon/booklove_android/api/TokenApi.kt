package com.moon.booklove_android.api

import com.moon.booklove_android.data.dto.ReissuanceResDTO
import com.moon.booklove_android.data.dto.SingleResult
import retrofit2.Call
import retrofit2.http.*

interface TokenApi {

    // Access & Refresh 토큰 재발급
    @PATCH("api/user/refresh")
    fun reissuance(): Call<SingleResult<ReissuanceResDTO>>

}