package com.moon.booklove_android.view.detail.presenter

import com.moon.booklove_android.view.detail.DetailContract

interface DetailPresenter : DetailContract.Presenter {
    override var view: DetailContract.View
}