package com.moon.booklove_android.view.login

interface LoginView : LoginContract.View {
    override fun loginComplete(res:String)
    override fun goSignUpPage()
    override fun kakaoLogin()
}