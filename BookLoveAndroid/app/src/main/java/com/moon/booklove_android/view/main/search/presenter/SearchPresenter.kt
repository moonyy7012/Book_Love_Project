package com.moon.booklove_android.view.main.search.presenter

import android.content.Context
import com.moon.booklove_android.view.main.search.SearchContract

interface SearchPresenter : SearchContract.Presenter {
    override var view: SearchContract.View
    override fun getBookListSearch(context: Context, search:String)
}