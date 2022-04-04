package com.moon.booklove_android.view.main.recent.recentbook.presenter

import android.content.Context
import com.moon.booklove_android.view.main.recent.recentbook.RecentBookContract

interface RecentBookPresenter : RecentBookContract.Presenter {
    override var view: RecentBookContract.View
    override fun getBookListRecent(context: Context)
}