package com.moon.booklove_android.view.collect.collectCategory

import android.content.Context
import com.moon.booklove_android.data.dto.UserInputInfoReqDTO

interface CollectCategoryContract {
    interface View {
        fun init()
        fun goMain()
    }
    interface Presenter {
        var view: View
        fun updateUserInfo(userInputInfoReqDTO: UserInputInfoReqDTO, context: Context)
    }
}