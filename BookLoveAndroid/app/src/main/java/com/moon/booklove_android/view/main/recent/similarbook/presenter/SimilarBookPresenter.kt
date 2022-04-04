package com.moon.booklove_android.view.main.recent.similarbook.presenter

import android.content.Context
import com.moon.booklove_android.view.main.recent.similarbook.SimilarBookContract

interface SimilarBookPresenter : SimilarBookContract.Presenter {
    override var view: SimilarBookContract.View
    override fun getBookListSimilar(context: Context)
}