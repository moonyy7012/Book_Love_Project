package com.moon.booklove_android.view.login.presenter

import android.content.Context
import com.moon.booklove_android.config.ApplicationClass.Companion.currentuser
import com.moon.booklove_android.config.ApplicationClass.Companion.initRetrofit
import com.moon.booklove_android.config.ApplicationClass.Companion.prefs
import com.moon.booklove_android.config.toast
import com.moon.booklove_android.config.util.RetrofitCallback
import com.moon.booklove_android.data.dto.LoginResDTO
import com.moon.booklove_android.data.dto.SingleResult
import com.moon.booklove_android.data.model.User
import com.moon.booklove_android.view.login.LoginContract
import com.moon.booklove_android.service.UserService

class LoginPresenterImpl(override var view: LoginContract.View) : LoginPresenter {

    override fun socialLogin(context: Context) {

        UserService().socialSignUp(object : RetrofitCallback<SingleResult<LoginResDTO>> {
            override fun onSuccess(code: Int, responseData: SingleResult<LoginResDTO>) {
                if (responseData.output == 1) {

                    toast("로그인 성공!",context)
                    prefs.setJWTAccess(responseData.data.accessToken)
                    prefs.setJWTRefresh(responseData.data.refreshToken)
                    initRetrofit()

                    currentuser = User(
                        responseData.data.id,
                        responseData.data.nickname,
                        responseData.data.age,
                        responseData.data.gender,
                        responseData.data.checked,
                        responseData.data.type,
                        responseData.data.userId,
                        responseData.data.userCategoryList)

                    prefs.setUserId(responseData.data.userId)
                    prefs.setLoginType(responseData.data.type)

                    if(responseData.data.checked){
                        view.loginComplete("first login")
                    }else{
                        view.loginComplete("")
                    }
                } else {
                    toast("문제가 발생하였습니다. 다시 시도해주세요.",context)
                }
            }

            override fun onFailure(code: Int) { toast("문제가 발생하였습니다. 다시 시도해주세요.", context) }

            override fun onError(t: Throwable) { toast("문제가 발생하였습니다. 다시 시도해주세요.",context) }

            override fun onExpired(code: Int) {}
        })
    }

    override fun normalLogin(id: String, password: String, context: Context) {

        UserService().normalLogin(id, password, object :
            RetrofitCallback<SingleResult<LoginResDTO>> {
            override fun onSuccess(code: Int, responseData: SingleResult<LoginResDTO>) {
                if (responseData.output==1) {

                    toast("로그인 성공!",context)
                    prefs.setJWTAccess(responseData.data.accessToken)
                    prefs.setJWTRefresh(responseData.data.refreshToken)
                    initRetrofit()

                    currentuser = User(
                        responseData.data.id,
                        responseData.data.nickname,
                        responseData.data.age,
                        responseData.data.gender,
                        responseData.data.checked,
                        responseData.data.type,
                        responseData.data.userId,
                        responseData.data.userCategoryList)

                    prefs.setUserId(responseData.data.userId)
                    prefs.setLoginType(responseData.data.type)

                    if(responseData.data.checked){
                        view.loginComplete("first login")
                    }else{
                        view.loginComplete("")
                    }
                } else {
                    toast("문제가 발생하였습니다. 다시 시도해주세요.",context)
                }
            }

            override fun onFailure(code: Int) { toast("문제가 발생하였습니다. 다시 시도해주세요.",context) }

            override fun onError(t: Throwable) { toast("문제가 발생하였습니다. 다시 시도해주세요.",context) }

            override fun onExpired(code: Int) {}
        })
    }

    override fun autoNormalLogin(userId: String, type: String, context: Context) {

        UserService().autoNormalLogin(userId, type, object :
            RetrofitCallback<SingleResult<LoginResDTO>> {
            override fun onSuccess(code: Int, responseData: SingleResult<LoginResDTO>) {
                if (responseData.output==1) {

                    toast("로그인 성공!",context)
                    prefs.setJWTAccess(responseData.data.accessToken)
                    prefs.setJWTRefresh(responseData.data.refreshToken)
                    initRetrofit()

                    currentuser = User(
                        responseData.data.id,
                        responseData.data.nickname,
                        responseData.data.age,
                        responseData.data.gender,
                        responseData.data.checked,
                        responseData.data.type,
                        responseData.data.userId,
                        responseData.data.userCategoryList)

                    prefs.setUserId(responseData.data.userId)
                    prefs.setLoginType(responseData.data.type)

                    if(responseData.data.checked){
                        view.loginComplete("first login")
                    }else{
                        view.loginComplete("")
                    }
                } else {
                    toast("문제가 발생하였습니다. 다시 시도해주세요.",context)
                }
            }

            override fun onFailure(code: Int) { toast("문제가 발생하였습니다. 다시 시도해주세요.",context) }

            override fun onError(t: Throwable) { toast("문제가 발생하였습니다. 다시 시도해주세요.",context) }

            override fun onExpired(code: Int) {}
        })
    }
}