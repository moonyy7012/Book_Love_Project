package com.moon.booklove_android.view.main.myPage.presenter

import android.content.Context
import com.moon.booklove_android.view.main.myPage.MyPageContract

interface MyPagePresenter : MyPageContract.Presenter {
    override var view: MyPageContract.View
    override fun userUpdateInfo(nickname: String, context: Context)
}