package com.moon.booklove_android.view.main.myPage.editGender.presenter

import android.content.Context
import com.moon.booklove_android.config.ApplicationClass.Companion.currentuser
import com.moon.booklove_android.config.ApplicationClass.Companion.initRetrofit
import com.moon.booklove_android.config.ApplicationClass.Companion.prefs
import com.moon.booklove_android.config.toast
import com.moon.booklove_android.config.util.RetrofitCallback
import com.moon.booklove_android.data.dto.*
import com.moon.booklove_android.view.main.myPage.editGender.EditGenderContract
import com.moon.booklove_android.service.TokenService
import com.moon.booklove_android.service.UserService

class EditGenderPresenterImpl(override var view: EditGenderContract.View) : EditGenderPresenter {

    override fun updateUserGender(userInfoUpdateReqDTO: UserInfoUpdateReqDTO, context: Context) {

        currentuser.gender = userInfoUpdateReqDTO.gender

        UserService().userInfoUpdate(userInfoUpdateReqDTO, object :
            RetrofitCallback<SingleResult<Any>> {
            override fun onSuccess(code: Int, responseData: SingleResult<Any>) {
                if (responseData.data==true) {
                    toast("성별을 수정했습니다", context)
                    view.selectComplete()
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
                            updateUserGender(userInfoUpdateReqDTO, context)
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