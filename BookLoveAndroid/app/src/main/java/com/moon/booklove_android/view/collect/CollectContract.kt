package com.moon.booklove_android.view.collect

interface CollectContract {
    interface View {
        fun openFragment(num: Int, gender:String, ageRange:Int)
    }
}