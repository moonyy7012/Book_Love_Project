package com.moon.booklove_android.view.collect.collectCategory

import android.content.Context
import com.moon.booklove_android.data.dto.UserInfoUpdateReqDTO

interface CollectCategoryContract {
    interface View {
        fun init()
        fun goMain()
    }
    interface Presenter {
        var view: View
        fun updateUserInfo(userInfoUpdateReqDTO: UserInfoUpdateReqDTO, context: Context)
    }
}