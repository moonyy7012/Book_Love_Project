package com.moon.booklove_android.view.main.home

interface HomeView : HomeContract.View {
    override fun bindInfo()
    override fun bindBanner()
}