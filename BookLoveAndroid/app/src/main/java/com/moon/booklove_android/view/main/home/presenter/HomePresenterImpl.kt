package com.moon.booklove_android.view.main.home.presenter

import android.content.Context
import android.util.Log
import com.moon.booklove_android.adapter.BookRecommAdapter
import com.moon.booklove_android.adapter.HomeBannerAdapter
import com.moon.booklove_android.config.ApplicationClass
import com.moon.booklove_android.config.ApplicationClass.Companion.bookInfo
import com.moon.booklove_android.config.ApplicationClass.Companion.bookRecommData
import com.moon.booklove_android.config.ApplicationClass.Companion.bookRecommandAdapter
import com.moon.booklove_android.config.ApplicationClass.Companion.homeBannerAdapter
import com.moon.booklove_android.config.ApplicationClass.Companion.homeBannerData
import com.moon.booklove_android.config.getRecomm
import com.moon.booklove_android.config.toast
import com.moon.booklove_android.config.util.RetrofitCallback
import com.moon.booklove_android.data.dto.BookInfoResDTO
import com.moon.booklove_android.data.dto.BookMainListResDTO
import com.moon.booklove_android.data.dto.ReissuanceResDTO
import com.moon.booklove_android.data.dto.SingleResult
import com.moon.booklove_android.service.BookService
import com.moon.booklove_android.service.TokenService
import com.moon.booklove_android.view.main.home.HomeContract

class HomePresenterImpl(override var view: HomeContract.View) : HomePresenter {
    override fun getBookListMain(context: Context) {
        BookService().getBookListMain(object :
            RetrofitCallback<SingleResult<BookMainListResDTO>> {
            override fun onSuccess(code: Int, res: SingleResult<BookMainListResDTO>) {
                if (res.output==1) {
                        bookRecommData = getRecomm(res.data)
                        bookRecommandAdapter = BookRecommAdapter()
                        displayMainList()
                        bookRecommandAdapter!!.submitList(bookRecommData)
                        view.bindInfo()

                        homeBannerAdapter = HomeBannerAdapter()
                        homeBannerData = mutableListOf()
                        addHomeBannerData()
                        homeBannerAdapter!!.submitList(homeBannerData)
                        view.bindBanner()

                } else {
                    toast("문제가 발생하였습니다. 다시 시도해주세요.",context)
                }
            }
            fun displayMainList(){
                for(a in 0..4){
                    if(bookRecommData!![a].books.size==0){
                        bookRecommData!![a].header=""
                    }
                }
            }
            fun addHomeBannerData(){
                var idx = listOf<Int>(2, 3, 0)
                for(i in idx){
                    if(bookRecommData!![i].books.size!=0){
                        var range = (0..bookRecommData!![i].books.size-1)
                        homeBannerData!!.add(bookRecommData!![i].books[range.random()])
                    }
                }
            }
            override fun onFailure(code: Int) {
                toast("문제가 발생하였습니다. 다시 시도해주세요.",context)
            }

            override fun onError(t: Throwable) {
                toast("문제가 발생하였습니다. 다시 시도해주세요.",context)
            }

            override fun onExpired(code: Int) {
                TokenService().reissuance(object :
                    RetrofitCallback<SingleResult<ReissuanceResDTO>> {
                    override fun onSuccess(code: Int, responseData: SingleResult<ReissuanceResDTO>) {
                        if (responseData.output==1) {
                            ApplicationClass.prefs.setJWTAccess(responseData.data.accessToken)
                            ApplicationClass.prefs.setJWTRefresh(responseData.data.refreshToken)
                            ApplicationClass.initRetrofit()
                            getBookListMain(context)
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