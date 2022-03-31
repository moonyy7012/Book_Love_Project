package com.moon.booklove_android.service

import com.moon.booklove_android.data.dto.ReissuanceResDTO
import com.moon.booklove_android.data.dto.SingleResult
import com.moon.booklove_android.config.util.RetrofitCallback
import com.moon.booklove_android.config.util.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TokenService {

    fun reissuance(callback: RetrofitCallback<SingleResult<ReissuanceResDTO>>) {
        RetrofitUtil.tokenService.reissuance()
            .enqueue(object : Callback<SingleResult<ReissuanceResDTO>> {
            override fun onResponse(call: Call<SingleResult<ReissuanceResDTO>>, response: Response<SingleResult<ReissuanceResDTO>>) {
                val res = response.body()
                if (response.code() == 200) {
                    if (res != null) {
                        callback.onSuccess(response.code(), res)
                    }
                } else if(response.code() == 403){
                    callback.onExpired(response.code())
                }else {
                    callback.onFailure(response.code())
                }
            }

            override fun onFailure(call: Call<SingleResult<ReissuanceResDTO>>, t: Throwable) {
                callback.onError(t)
            }
        })
    }
}
