package com.moon.booklove_android.util

import com.moon.booklove_android.api.TokenApi
import com.moon.booklove_android.api.UserApi
import com.moon.booklove_android.config.ApplicationClass


class RetrofitUtil {

    companion object {
        val userService = ApplicationClass.retrofit.create(UserApi::class.java)
        val tokenService = ApplicationClass.retrofit.create(TokenApi::class.java)

      }
}
