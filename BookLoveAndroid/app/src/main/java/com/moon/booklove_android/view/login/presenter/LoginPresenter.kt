package com.moon.booklove_android.view.login.presenter

import android.content.Context
import com.moon.booklove_android.view.login.LoginContract

interface LoginPresenter : LoginContract.Presenter {
    override var view: LoginContract.View
    override fun socialLogin(context: Context)
    override fun normalLogin(id: String, password: String, context: Context)
    override fun autoNormalLogin(userId: String, type: String, context: Context)
}