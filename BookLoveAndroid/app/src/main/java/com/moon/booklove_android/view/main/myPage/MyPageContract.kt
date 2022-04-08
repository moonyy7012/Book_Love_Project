package com.moon.booklove_android.view.main.myPage

import android.content.Context

interface MyPageContract {
    interface View {
        fun init()
        fun logout()
    }

    interface Presenter {
        var view: View
        fun userUpdateNickName(nickname: String, context: Context)
        fun userUpdatePassword(prevPw: String, updatePw: String, context: Context)
    }
}