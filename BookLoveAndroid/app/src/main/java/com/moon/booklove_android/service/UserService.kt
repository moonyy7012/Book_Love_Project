package com.moon.booklove_android.service

import com.moon.booklove_android.dto.*
import com.moon.booklove_android.util.RetrofitCallback
import com.moon.booklove_android.util.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserService {

    fun normalSignUp(normalSignUpReqDTO: NormalSignUpReqDTO, callback: RetrofitCallback<SingleResult<NormalSignUpResDTO>>) {
        RetrofitUtil.userService.normalSignUp(normalSignUpReqDTO)
            .enqueue(object : Callback<SingleResult<NormalSignUpResDTO>> {
            override fun onResponse(call: Call<SingleResult<NormalSignUpResDTO>>, response: Response<SingleResult<NormalSignUpResDTO>>) {
                val res = response.body()
                if (response.code() == 200) {
                    if (res != null) {
                        callback.onSuccess(response.code(), res)
                    }
                } else {
                    callback.onFailure(response.code())
                }
            }

            override fun onFailure(call: Call<SingleResult<NormalSignUpResDTO>>, t: Throwable) {
                callback.onError(t)
            }
        })
    }

    fun normalLogin(normalLoginReqDTO: NormalLoginReqDTO, callback: RetrofitCallback<SingleResult<NormalLoginResDTO>>) {
        RetrofitUtil.userService.normalLogin(normalLoginReqDTO)
            .enqueue(object : Callback<SingleResult<NormalLoginResDTO>> {
                override fun onResponse(call: Call<SingleResult<NormalLoginResDTO>>, response: Response<SingleResult<NormalLoginResDTO>>) {
                    val res = response.body()
                    if (response.code() == 200) {
                        if (res != null) {
                            callback.onSuccess(response.code(), res)
                        }
                    } else {
                        callback.onFailure(response.code())
                    }
                }

                override fun onFailure(call: Call<SingleResult<NormalLoginResDTO>>, t: Throwable) {
                    callback.onError(t)
                }
            })
    }

    fun socialSignUp(callback: RetrofitCallback<SingleResult<SocialLoginResDTO>>) {
        RetrofitUtil.userService.socialSignUp()
            .enqueue(object : Callback<SingleResult<SocialLoginResDTO>> {
                override fun onResponse(call: Call<SingleResult<SocialLoginResDTO>>, response: Response<SingleResult<SocialLoginResDTO>>) {
                    val res = response.body()
                    if (response.code() == 200) {
                        if (res != null) {
                            callback.onSuccess(response.code(), res)
                        }
                    } else {
                        callback.onFailure(response.code())
                    }
                }

                override fun onFailure(call: Call<SingleResult<SocialLoginResDTO>>, t: Throwable) {
                    callback.onError(t)
                }
            })
    }

    fun checkID(userId: String, callback: RetrofitCallback<SingleResult<CheckIDResDTO>>) {
        RetrofitUtil.userService.checkID(userId)
            .enqueue(object : Callback<SingleResult<CheckIDResDTO>> {
                override fun onResponse(call: Call<SingleResult<CheckIDResDTO>>, response: Response<SingleResult<CheckIDResDTO>>) {
                    val res = response.body()
                    if (response.code() == 200) {
                        if (res != null) {
                            callback.onSuccess(response.code(), res)
                        }
                    } else {
                        callback.onFailure(response.code())
                    }
                }

                override fun onFailure(call: Call<SingleResult<CheckIDResDTO>>, t: Throwable) {
                    callback.onError(t)
                }
            })
    }

    fun userInfoUpdate(userInfoUpdateReqDTO: UserInfoUpdateReqDTO, callback: RetrofitCallback<SingleResult<UserInfoUpdateResDTO>>) {
        RetrofitUtil.userService.userInfoUpdate(userInfoUpdateReqDTO).enqueue(object : Callback<SingleResult<UserInfoUpdateResDTO>> {
            override fun onResponse(call: Call<SingleResult<UserInfoUpdateResDTO>>, response: Response<SingleResult<UserInfoUpdateResDTO>>) {
                val res = response.body()
                if (response.code() == 200) {
                    if (res != null) {
                        callback.onSuccess(response.code(), res)
                    }
                } else {
                    callback.onFailure(response.code())
                }
            }

            override fun onFailure(call: Call<SingleResult<UserInfoUpdateResDTO>>, t: Throwable) {
                callback.onError(t)
            }
        })
    }
}
