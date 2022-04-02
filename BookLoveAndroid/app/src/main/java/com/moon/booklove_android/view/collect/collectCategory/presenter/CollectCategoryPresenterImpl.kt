package com.moon.booklove_android.view.collect.collectCategory.presenter

import android.content.Context
import com.moon.booklove_android.view.collect.collectCategory.CollectCategoryContract
import com.moon.booklove_android.config.ApplicationClass.Companion.currentuser
import com.moon.booklove_android.config.ApplicationClass.Companion.initRetrofit
import com.moon.booklove_android.config.ApplicationClass.Companion.prefs
import com.moon.booklove_android.config.toast
import com.moon.booklove_android.config.util.RetrofitCallback
import com.moon.booklove_android.data.dto.*
import com.moon.booklove_android.service.TokenService
import com.moon.booklove_android.service.UserService

class CollectCategoryPresenterImpl(override var view: CollectCategoryContract.View) : CollectCategopryPresenter {

    override fun updateUserInfo(userInputInfoReqDTO: UserInputInfoReqDTO, context: Context) {

        currentuser.age = userInputInfoReqDTO.age
        currentuser.gender = userInputInfoReqDTO.gender
        currentuser.userCategoryList = userInputInfoReqDTO.categories

        UserService().userInputInfo(userInputInfoReqDTO, object :
            RetrofitCallback<SingleResult<Any>> {

            override fun onSuccess(code: Int, responseData: SingleResult<Any>) {
                if (responseData.data==true) {
                    toast("정보 저장 성공!", context)
                    view.goMain()
                } else {
                    toast("문제가 발생하였습니다. 다시 시도해주세요.", context)
                }
            }

            override fun onFailure(code: Int) { toast( "문제가 발생하였습니다. 다시 시도해주세요.",context) }

            override fun onError(t: Throwable) { toast("문제가 발생하였습니다. 다시 시도해주세요.", context) }

            override fun onExpired(code: Int) {
                super.onExpired(code)
                TokenService().reissuance(object :
                    RetrofitCallback<SingleResult<ReissuanceResDTO>> {
                    override fun onSuccess(code: Int, responseData: SingleResult<ReissuanceResDTO>) {
                        if (responseData.output==1) {
                            prefs.setJWTAccess(responseData.data.accessToken)
                            prefs.setJWTRefresh(responseData.data.refreshToken)
                            initRetrofit()
                            updateUserInfo(userInputInfoReqDTO, context)
                        } else toast("문제가 발생하였습니다. 다시 시도해주세요.", context)
                    }

                    override fun onFailure(code: Int) { toast("유효하지 못한 접근입니다.", context) }

                    override fun onError(t: Throwable) { toast("유효하지 못한 접근입니다.", context) }

                    override fun onExpired(code: Int) {}
                })
            }
        })
    }
}