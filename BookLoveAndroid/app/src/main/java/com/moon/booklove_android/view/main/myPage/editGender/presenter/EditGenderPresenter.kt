package com.moon.booklove_android.view.main.myPage.editGender.presenter

import android.content.Context
import com.moon.booklove_android.data.dto.UserInfoUpdateReqDTO
import com.moon.booklove_android.view.main.myPage.editGender.EditGenderContract

interface EditGenderPresenter : EditGenderContract.Presenter {
    override var view: EditGenderContract.View
    override fun updateUserGender(userInfoUpdateReqDTO: UserInfoUpdateReqDTO, context: Context)
}