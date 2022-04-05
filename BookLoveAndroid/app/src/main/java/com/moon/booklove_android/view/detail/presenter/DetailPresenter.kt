package com.moon.booklove_android.view.detail.presenter

import android.content.Context
import com.moon.booklove_android.view.detail.DetailContract

interface DetailPresenter : DetailContract.Presenter {
    override var view: DetailContract.View
    override fun getBookInfo(context: Context, bookId: Long)
}