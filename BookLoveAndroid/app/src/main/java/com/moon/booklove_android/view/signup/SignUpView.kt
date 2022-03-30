package com.moon.booklove_android.view.signup

interface SignUpView : SignUpContract.View {
    override fun idCheck(check:Boolean)
    override fun signUp()
    override fun signUpComplete()
}