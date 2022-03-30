package com.moon.booklove_android.view.main.category

import android.content.Context

interface CategoryContract {
    interface View {
        fun connectAdapter()
    }

    interface Presenter {
        var view: View
        fun getBookListCategory(context: Context, category:String)

    }
}