package com.moon.booklove_android.view.collect

interface CollectView : CollectContract.View {
    override fun openFragment(num: Int, gender:String, ageRange:Int)
}