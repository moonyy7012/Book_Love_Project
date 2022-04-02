package com.moon.booklove_android.view.main.myPage.editAge.presenter

import android.content.Context
import com.moon.booklove_android.data.dto.UserInputInfoReqDTO
import com.moon.booklove_android.view.main.myPage.editAge.EditAgeContract

interface EditAgePresenter : EditAgeContract.Presenter {
    override var view: EditAgeContract.View
    override fun updateUserAge(userInputInfoReqDTO: UserInputInfoReqDTO, context: Context)
}