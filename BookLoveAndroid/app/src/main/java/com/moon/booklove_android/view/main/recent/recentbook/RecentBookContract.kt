package com.moon.booklove_android.view.main.recent.recentbook

import android.content.Context

interface RecentBookContract {
    interface View {
        fun bindInfo()
    }
    interface Presenter {
        var view: View
        fun getBookListRecent(context: Context)
    }
}