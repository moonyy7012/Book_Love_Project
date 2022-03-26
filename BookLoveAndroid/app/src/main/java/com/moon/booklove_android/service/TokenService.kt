package com.moon.booklove_android.service

import com.moon.booklove_android.dto.ReissuanceResDTO
import com.moon.booklove_android.dto.SingleResult
import com.moon.booklove_android.util.RetrofitCallback
import com.moon.booklove_android.util.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TokenService {

//    fun login(password: String, type: String, uid: String, callback: RetrofitCallback<SingleResult<LoginUserResDTO>>) {
//        RetrofitUtil.userService.login(password, type, uid)
//            .enqueue(object : Callback<SingleResult<LoginUserResDTO>> {
//                override fun onResponse(call: Call<SingleResult<LoginUserResDTO>>, response: Response<SingleResult<LoginUserResDTO>>) {
//                    val res = response.body()
//                    if (response.code() == 200) {
//                        if (res != null) {
//                            callback.onSuccess(response.code(), res)
//                        }
//                    } else {
//                        callback.onFailure(response.code())
//                    }
//                }
//
//                override fun onFailure(call: Call<SingleResult<LoginUserResDTO>>, t: Throwable) {
//                    callback.onError(t)
//                }
//            })
//    }

    fun reissuance(callback: RetrofitCallback<SingleResult<ReissuanceResDTO>>) {
        RetrofitUtil.tokenService.reissuance()
            .enqueue(object : Callback<SingleResult<ReissuanceResDTO>> {
            override fun onResponse(call: Call<SingleResult<ReissuanceResDTO>>, response: Response<SingleResult<ReissuanceResDTO>>) {
                val res = response.body()
                if (response.code() == 200) {
                    if (res != null) {
                        callback.onSuccess(response.code(), res)
                    }
                } else {
                    callback.onFailure(response.code())
                }
            }

            override fun onFailure(call: Call<SingleResult<ReissuanceResDTO>>, t: Throwable) {
                callback.onError(t)
            }
        })
    }

//    fun userInfoUpdate(user: UserInfoUpdateReqDTO, callback: RetrofitCallback<SingleResult<UserIdResDTO>>) {
//        RetrofitUtil.userService.userInfoUpdate(user).enqueue(object : Callback<SingleResult<UserIdResDTO>> {
//            override fun onResponse(call: Call<SingleResult<UserIdResDTO>>, response: Response<SingleResult<UserIdResDTO>>) {
//                val res = response.body()
//                if (response.code() == 200) {
//                    if (res != null) {
//                        callback.onSuccess(response.code(), res)
//                    }
//                } else {
//                    callback.onFailure(response.code())
//                }
//            }
//
//            override fun onFailure(call: Call<SingleResult<UserIdResDTO>>, t: Throwable) {
//                callback.onError(t)
//            }
//        })
//    }
}
