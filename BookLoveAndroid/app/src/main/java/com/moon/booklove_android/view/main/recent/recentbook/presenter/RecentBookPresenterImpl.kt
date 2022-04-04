package com.moon.booklove_android.view.main.recent.recentbook.presenter

import android.content.Context
import com.moon.booklove_android.adapter.RecentBookAdapter
import com.moon.booklove_android.config.ApplicationClass.Companion.bookRecentAdapter
import com.moon.booklove_android.config.ApplicationClass.Companion.initRetrofit
import com.moon.booklove_android.config.ApplicationClass.Companion.prefs
import com.moon.booklove_android.config.toast
import com.moon.booklove_android.config.util.RetrofitCallback
import com.moon.booklove_android.data.dto.*
import com.moon.booklove_android.service.BookService
import com.moon.booklove_android.service.TokenService
import com.moon.booklove_android.view.main.recent.recentbook.RecentBookContract

class RecentBookPresenterImpl(override var view: RecentBookContract.View) : RecentBookPresenter {

    override fun getBookListRecent(context: Context) {

        BookService().getBookListRecent(object : RetrofitCallback<SingleResult<BookRecentListResDTO>> {
            override fun onSuccess(code: Int, responseData: SingleResult<BookRecentListResDTO>) {
                if (responseData.output==1) {
                    val bookList = responseData
                    bookRecentAdapter = RecentBookAdapter()
                    bookRecentAdapter!!.submitList(bookList.data.bookRecentList)
                    view.bindInfo()
                } else {
                    toast("문제가 발생하였습니다. 다시 시도해주세요.", context)
                }
            }

            override fun onFailure(code: Int) { toast( "문제가 발생하였습니다. 다시 시도해주세요.",context) }

            override fun onError(t: Throwable) { toast("문제가 발생하였습니다. 다시 시도해주세요.", context) }

            override fun onExpired(code: Int) {
                super.onExpired(code)
                TokenService().reissuance(object :
                    RetrofitCallback<SingleResult<ReissuanceResDTO>> {
                    override fun onSuccess(code: Int, responseData: SingleResult<ReissuanceResDTO>) {
                        if (responseData.output==1) {
                            prefs.setJWTAccess(responseData.data.accessToken)
                            prefs.setJWTRefresh(responseData.data.refreshToken)
                            initRetrofit()
                            getBookListRecent(context)
                        } else toast("문제가 발생하였습니다. 다시 시도해주세요.", context)
                    }

                    override fun onFailure(code: Int) { toast("유효하지 못한 접근입니다.", context) }

                    override fun onError(t: Throwable) { toast("유효하지 못한 접근입니다.", context) }

                    override fun onExpired(code: Int) {}
                })
            }
        })
    }
}