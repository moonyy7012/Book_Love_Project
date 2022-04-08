package com.moon.booklove_android.view.main.myPage.editCategory

import android.content.Context
import com.moon.booklove_android.data.dto.UserInputInfoReqDTO

interface EditCategoryContract {
    interface View {
        fun init()
        fun selectComplete()
    }
    interface Presenter {
        var view: View
        fun updateUserCategory(userInputInfoReqDTO: UserInputInfoReqDTO, context: Context)
    }
}