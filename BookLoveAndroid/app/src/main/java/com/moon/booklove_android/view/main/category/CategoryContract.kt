package com.moon.booklove_android.view.main.category

interface CategoryContract {
    interface View {
    }

    interface Presenter {
        var view: View
    }
}