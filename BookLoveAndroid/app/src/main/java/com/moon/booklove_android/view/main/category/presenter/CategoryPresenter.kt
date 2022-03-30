package com.moon.booklove_android.view.main.category.presenter

import android.content.Context
import com.moon.booklove_android.view.main.category.CategoryContract

interface CategoryPresenter : CategoryContract.Presenter {
    override var view: CategoryContract.View
    override fun getBookListCategory(context: Context, category:String, position:Int)

}