package com.moon.booklove_android.view.login

import android.content.Context

interface LoginContract {
    interface View {
        fun kakaoLogin()
        fun goSignUpPage()
        fun loginComplete(res:String)
    }

    interface Presenter {
        var view: View
        fun socialLogin(context: Context)
        fun normalLogin(id:String, password:String, context: Context)
        fun autoLogin(context: Context)
    }
}