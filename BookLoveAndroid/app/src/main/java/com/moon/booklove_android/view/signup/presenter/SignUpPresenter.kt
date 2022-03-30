package com.moon.booklove_android.view.signup.presenter

import android.content.Context
import com.moon.booklove_android.data.dto.NormalSignUpReqDTO
import com.moon.booklove_android.view.signup.SignUpContract

interface SignUpPresenter : SignUpContract.Presenter {
    override var view: SignUpContract.View
    override fun normalSignUp(normalSignUpReqDTO: NormalSignUpReqDTO, context: Context)
    override fun checkID(userId:String, context: Context)
}