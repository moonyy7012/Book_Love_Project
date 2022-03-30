package com.moon.booklove_android.view.collect.collectGender

interface CollectGenderView : CollectGenderContract.View {
    override fun selectMan()
    override fun selectWoman()
    override fun goCollectAge()
}