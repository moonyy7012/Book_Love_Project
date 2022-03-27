package com.moon.booklove_android.api

import com.moon.booklove_android.dto.ReissuanceResDTO
import com.moon.booklove_android.dto.SingleResult
import retrofit2.Call
import retrofit2.http.*

interface TokenApi {

    // 토큰 재발급
    @POST("api/user/refresh")
    fun reissuance(): Call<SingleResult<ReissuanceResDTO>>

}
