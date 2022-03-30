package com.moon.booklove_android.view.main.myPage.editAge

import androidx.appcompat.widget.AppCompatButton

interface EditAgeView : EditAgeContract.View {
    override fun init()
    override fun clickButton(clickedButton: AppCompatButton)
    override fun selectComplete()
}