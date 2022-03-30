package com.moon.booklove_android.view.main.home.presenter

import com.moon.booklove_android.view.main.home.HomeContract

interface HomePresenter : HomeContract.Presenter {
    override var view: HomeContract.View
}