package com.moon.booklove_android.view.main.myPage.editAge

import android.content.Context
import androidx.appcompat.widget.AppCompatButton
import com.moon.booklove_android.data.dto.UserInfoUpdateReqDTO

interface EditAgeContract {
    interface View {
        fun init()
        fun clickButton(clickedButton: AppCompatButton)
        fun selectComplete()
    }
    interface Presenter {
        var view: View
        fun updateUserAge(userInfoUpdateReqDTO: UserInfoUpdateReqDTO, context: Context)
    }
}