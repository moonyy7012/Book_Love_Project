package com.moon.booklove_android.view.signup

import android.content.Context
import com.moon.booklove_android.data.dto.NormalSignUpReqDTO

interface SignUpContract {
    interface View {
        fun idCheck(check:Boolean)
        fun signUp()
        fun signUpComplete()
    }

    interface Presenter {
        var view: View
        fun normalSignUp(normalSignUpReqDTO: NormalSignUpReqDTO, context: Context)
        fun checkID(userId:String, context: Context)
    }
}