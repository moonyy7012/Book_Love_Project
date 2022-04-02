package com.moon.booklove_android.view.collect.collectCategory.presenter

import android.content.Context
import com.moon.booklove_android.view.collect.collectCategory.CollectCategoryContract
import com.moon.booklove_android.data.dto.UserInputInfoReqDTO

interface CollectCategopryPresenter : CollectCategoryContract.Presenter {
    override var view: CollectCategoryContract.View
    override fun updateUserInfo(userInputInfoReqDTO: UserInputInfoReqDTO, context: Context)
}