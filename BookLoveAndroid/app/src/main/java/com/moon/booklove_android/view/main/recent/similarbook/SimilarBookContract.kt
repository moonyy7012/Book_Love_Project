package com.moon.booklove_android.view.main.recent.similarbook

import android.content.Context

interface SimilarBookContract {
    interface View {
        fun bindInfo()
    }
    interface Presenter {
        var view: View
        fun getBookListSimilar(context: Context)
    }
}