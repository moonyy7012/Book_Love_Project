package com.moon.booklove_android.view.main.myPage.editCategory.presenter

import android.content.Context
import com.moon.booklove_android.data.dto.UserInputInfoReqDTO
import com.moon.booklove_android.view.main.myPage.editCategory.EditCategoryContract

interface EditCategoryPresenter : EditCategoryContract.Presenter {
    override var view: EditCategoryContract.View
    override fun updateUserCategory(userInputInfoReqDTO: UserInputInfoReqDTO, context: Context)
}