package com.moon.booklove_android.view.main.myPage.presenter

import android.content.Context
import com.moon.booklove_android.view.main.myPage.MyPageContract

interface MyPagePresenter : MyPageContract.Presenter {
    override var view: MyPageContract.View
    override fun userUpdateNickName(nickname: String, context: Context)
    override fun userUpdatePassword(prevPw: String, updatePw: String, context: Context)
}