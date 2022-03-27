package com.moon.booklove_android.service

import android.util.Log
import com.moon.booklove_android.dto.*
import com.moon.booklove_android.util.RetrofitCallback
import com.moon.booklove_android.util.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserService {

    fun normalSignUp(normalSignUpReqDTO: NormalSignUpReqDTO, callback: RetrofitCallback<SingleResult<Any>>) {
        RetrofitUtil.userService.normalSignUp(normalSignUpReqDTO)
            .enqueue(object : Callback<SingleResult<Any>> {
            override fun onResponse(call: Call<SingleResult<Any>>, response: Response<SingleResult<Any>>) {
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

            override fun onFailure(call: Call<SingleResult<Any>>, t: Throwable) {
                callback.onError(t)
            }
        })
    }

    fun normalLogin(id:String, password:String, callback: RetrofitCallback<SingleResult<NormalLoginResDTO>>) {
        RetrofitUtil.userService.normalLogin(id, password)
            .enqueue(object : Callback<SingleResult<NormalLoginResDTO>> {
                override fun onResponse(call: Call<SingleResult<NormalLoginResDTO>>, response: Response<SingleResult<NormalLoginResDTO>>) {
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
                    } else if(response.code() == 403){
                        callback.onExpired(response.code())
                    }else {
                        callback.onFailure(response.code())
                    }
                }

                override fun onFailure(call: Call<SingleResult<SocialLoginResDTO>>, t: Throwable) {
                    callback.onError(t)
                }
            })
    }

    fun checkID(userId: String, callback: RetrofitCallback<SingleResult<Any>>) {
        RetrofitUtil.userService.checkID(userId)
            .enqueue(object : Callback<SingleResult<Any>> {
                override fun onResponse(call: Call<SingleResult<Any>>, response: Response<SingleResult<Any>>) {
                    val res = response.body()
                    if (response.code() == 200) {
                        if (res != null) {
                            callback.onSuccess(response.code(), res)
                        }
                    } else if(response.code() == 403){
                        callback.onExpired(response.code())
                    } else{
                        callback.onFailure(response.code())
                    }
                }

                override fun onFailure(call: Call<SingleResult<Any>>, t: Throwable) {
                    callback.onError(t)
                }
            })
    }

    fun userInfoUpdate(userInfoUpdateReqDTO: UserInfoUpdateReqDTO, callback: RetrofitCallback<SingleResult<Any>>) {
        RetrofitUtil.userService.userInfoUpdate(userInfoUpdateReqDTO).enqueue(object : Callback<SingleResult<Any>> {
            override fun onResponse(call: Call<SingleResult<Any>>, response: Response<SingleResult<Any>>) {
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

            override fun onFailure(call: Call<SingleResult<Any>>, t: Throwable) {
                callback.onError(t)
            }
        })
    }
}
