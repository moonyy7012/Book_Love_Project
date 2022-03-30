package com.moon.booklove_android.view.main.myPage.editCategory

import android.content.Context
import com.moon.booklove_android.data.dto.UserInfoUpdateReqDTO

interface EditCategoryContract {
    interface View {
        fun init()
        fun selectComplete()
    }
    interface Presenter {
        var view: View
        fun updateUserCategory(userInfoUpdateReqDTO: UserInfoUpdateReqDTO, context: Context)
    }
}