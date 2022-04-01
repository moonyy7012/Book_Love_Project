package com.moon.booklove_android.view.main.home.presenter

import android.content.Context
import com.moon.booklove_android.view.main.home.HomeContract

interface HomePresenter : HomeContract.Presenter {
    override var view: HomeContract.View
    override fun getBookListMain(context: Context)
    override fun getBookInfo(context: Context, bookId: Long, idx:Int)
}