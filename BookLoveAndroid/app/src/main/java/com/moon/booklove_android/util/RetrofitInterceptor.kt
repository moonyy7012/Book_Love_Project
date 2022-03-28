package com.moon.booklove_android.util

import com.moon.booklove_android.config.ApplicationClass
import com.moon.booklove_android.config.ApplicationClass.Companion.prefs
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RetrofitInterceptor: Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {
        val newRequest = request().newBuilder()
            .addHeader("X-AUTH-TOKEN", prefs.getJWTAccess().toString())
            .build()
        proceed(newRequest)
    }
}