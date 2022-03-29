package com.moon.booklove_android.view.main.category.presenter

import com.moon.booklove_android.view.main.category.CategoryContract

interface CategoryPresenter : CategoryContract.Presenter {
    override var view: CategoryContract.View
}