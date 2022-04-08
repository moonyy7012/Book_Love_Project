package com.moon.booklove_android.view.main.search

import android.content.Context

interface SearchContract {
    interface View {
        fun connectAdapter()
    }

    interface Presenter {
        var view: View
        fun getBookListSearch(context: Context, search:String)
    }
}