package com.moon.booklove_android.view.signup.presenter

import android.content.Context
import com.moon.booklove_android.config.toast
import com.moon.booklove_android.config.util.RetrofitCallback
import com.moon.booklove_android.data.dto.NormalSignUpReqDTO
import com.moon.booklove_android.data.dto.SingleResult
import com.moon.booklove_android.service.UserService
import com.moon.booklove_android.view.signup.SignUpContract

class SignUpPresenterImpl(override var view: SignUpContract.View) : SignUpPresenter {

    override fun normalSignUp(normalSignUpReqDTO: NormalSignUpReqDTO, context: Context) {

        UserService().normalSignUp(normalSignUpReqDTO,object : RetrofitCallback<SingleResult<Any>> {
            override fun onSuccess(code: Int, responseData: SingleResult<Any>) {
                if (responseData.output==1) {
                    toast("회원가입이 완료되었습니다.",context)
                    view.signUpComplete()
                } else {
                    toast("문제가 발생하였습니다. 다시 시도해주세요.",context)
                }
            }

            override fun onFailure(code: Int) { toast("문제가 발생하였습니다. 다시 시도해주세요.",context) }

            override fun onError(t: Throwable) { toast("문제가 발생하였습니다. 다시 시도해주세요.",context) }

            override fun onExpired(code: Int) {}
        })
    }

    override fun checkID(userId: String, context: Context) {

        UserService().checkID(userId,object : RetrofitCallback<SingleResult<Boolean>> {
            override fun onSuccess(code: Int, responseData: SingleResult<Boolean>) {
                if (responseData.data==false) {
                    view.idCheck(false)
                    toast( "사용 가능한 아이디 입니다.",context)
                } else {
                    view.idCheck(true)
                    toast( "중복 되는 아이디 입니다. ",context)
                }
            }

            override fun onFailure(code: Int) { toast("문제가 발생하였습니다. 다시 시도해주세요.",context) }

            override fun onError(t: Throwable) { toast("문제가 발생하였습니다. 다시 시도해주세요.",context) }

            override fun onExpired(code: Int) {}
        })
    }
}
