package com.moon.booklove_android.view.main.myPage.editGender

interface EditGenderView : EditGenderContract.View {
    override fun init()
    override fun selectMan()
    override fun selectWoman()
    override fun selectComplete()
}