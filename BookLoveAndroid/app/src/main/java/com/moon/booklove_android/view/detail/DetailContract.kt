package com.moon.booklove_android.view.detail

import android.content.Context

interface DetailContract {
    interface View {
        fun bindInfo()
    }

    interface Presenter {
        var view: View
        fun getBookInfo(context: Context, bookId: Long)
    }
}