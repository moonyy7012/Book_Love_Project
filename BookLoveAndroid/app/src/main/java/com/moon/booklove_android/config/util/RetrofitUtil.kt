package com.moon.booklove_android.config.util

import com.moon.booklove_android.api.BookApi
import com.moon.booklove_android.api.TokenApi
import com.moon.booklove_android.api.UserApi
import com.moon.booklove_android.config.ApplicationClass

class RetrofitUtil {

    companion object {
        val userService = ApplicationClass.retrofit.create(UserApi::class.java)
        val tokenService = ApplicationClass.retrofit.create(TokenApi::class.java)
        val bookService = ApplicationClass.retrofit.create(BookApi::class.java)
      }
}
