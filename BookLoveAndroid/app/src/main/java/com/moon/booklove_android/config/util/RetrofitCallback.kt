package com.moon.booklove_android.config.util

import com.moon.booklove_android.config.ApplicationClass.Companion.initRetrofit
import com.moon.booklove_android.config.ApplicationClass.Companion.prefs

interface RetrofitCallback<T> {

    fun onError(t: Throwable)

    fun onSuccess(code: Int, responseData: T)

    fun onFailure(code: Int)

    fun onExpired(code: Int){
        prefs.setJWTAccess(prefs.getJWTRefresh().toString())
        initRetrofit()
    }
}