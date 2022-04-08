package com.moon.booklove_android.view.main.home

import android.content.Context

interface HomeContract {
    interface View {
        fun bindInfo()
        fun bindBanner()
    }

    interface Presenter {
        var view: View
        fun getBookListMain(context: Context)
    }
}