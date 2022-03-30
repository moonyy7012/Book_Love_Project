package com.moon.booklove_android.view.detail

interface DetailContract {
    interface View {
    }

    interface Presenter {
        var view: View
    }
}