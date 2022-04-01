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

private const val TAG = "HomePresenterImpl"
private var range = (0..bookRecommData!![2].books.size-1)

class HomePresenterImpl(override var view: HomeContract.View) : HomePresenter {
    override fun getBookListMain(context: Context) {
        BookService().getBookListMain(object :
            RetrofitCallback<SingleResult<BookMainListResDTO>> {
            override fun onSuccess(code: Int, res: SingleResult<BookMainListResDTO>) {
                if (res.output==1) {
                    bookRecommData = getRecomm(res.data)
                    bookRecommandAdapter = BookRecommAdapter()
                    bookRecommandAdapter!!.submitList(bookRecommData)
                    view.bindInfo()

                    homeBannerData = mutableListOf()
                    homeBannerAdapter = HomeBannerAdapter()
                    range = (0..bookRecommData!![2].books.size-1)

                    getBookInfo(context, bookRecommData!![2].books[range.random()].bookId, 2)



                } else {
                    toast("문제가 발생하였습니다. 다시 시도해주세요.",context)
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

    override fun getBookInfo(context: Context, bookId: Long, idx:Int) {
        BookService().getBookInfo(bookId, object :
            RetrofitCallback<SingleResult<BookInfoResDTO>> {

            override fun onSuccess(code: Int, responseData: SingleResult<BookInfoResDTO>) {
                if (responseData.output==1) {
                    bookInfo = responseData
                    homeBannerData!!.add(bookInfo!!.data)

                    if(idx==2){
                        range = (0..bookRecommData!![3].books.size-1)
                        getBookInfo(context, bookRecommData!![3].books[range.random()].bookId, 3)

                    }else if(idx==3){
                        range = (0..bookRecommData!![0].books.size-1)
                        getBookInfo(context, bookRecommData!![0].books[range.random()].bookId, 0)

                    }else {
                        homeBannerAdapter!!.submitList(homeBannerData)
                        view.bindInfo()
                    }

                } else {
                    toast("문제가 발생하였습니다. 다시 시도해주세요.",context)
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
                            if(idx==2){
                                range = (0..bookRecommData!![3].books.size-1)
                                getBookInfo(context, bookRecommData!![3].books[range.random()].bookId, 3)

                            }else if(idx==3){
                                range = (0..bookRecommData!![0].books.size-1)
                                getBookInfo(context, bookRecommData!![0].books[range.random()].bookId, 0)

                            }else {
                                homeBannerAdapter!!.submitList(homeBannerData)
                                view.bindInfo()
                            }
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