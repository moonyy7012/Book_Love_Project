package com.moon.booklove_android.view.detail.presenter

import android.content.Context
import com.moon.booklove_android.adapter.BookDetailAdapter
import com.moon.booklove_android.adapter.BookItemAdapter
import com.moon.booklove_android.config.ApplicationClass
import com.moon.booklove_android.config.ApplicationClass.Companion.bookDetailAdapter
import com.moon.booklove_android.config.ApplicationClass.Companion.bookInfo
import com.moon.booklove_android.config.ApplicationClass.Companion.bookSimilarAdapter
import com.moon.booklove_android.config.toast
import com.moon.booklove_android.config.util.RetrofitCallback
import com.moon.booklove_android.data.dto.*
import com.moon.booklove_android.service.BookService
import com.moon.booklove_android.service.TokenService
import com.moon.booklove_android.view.detail.DetailContract

class DetailPresenterImpl(override var view: DetailContract.View) : DetailPresenter {

    private var bookDetailValueList = mutableListOf<String>()
    override fun getBookInfo(context: Context, bookId: Long) {
        BookService().getBookInfo(bookId, object :
            RetrofitCallback<SingleResult<BookInfoResDTO>> {

            override fun onSuccess(code: Int, responseData: SingleResult<BookInfoResDTO>) {
                if (responseData.output==1) {
                    bookInfo = responseData
                    bookDetailAdapter = BookDetailAdapter()
                    createBookDetailValueList(bookInfo!!.data)


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
                            getBookInfo(context, bookId)
                        } else toast("문제가 발생하였습니다. 다시 시도해주세요.", context)
                    }

                    override fun onFailure(code: Int) { toast("유효하지 못한 접근입니다.", context) }

                    override fun onError(t: Throwable) { toast("유효하지 못한 접근입니다.", context) }

                    override fun onExpired(code: Int) {}
                })

            }
        })

    }

    private fun createBookDetailValueList (data :BookInfoResDTO){
        bookDetailValueList.apply {
            add(data.author)
            add(data.publisher)
            add(data.categoryName)
            add(data.priceStandard.toString())
            add(data.link)
        }
        bookDetailAdapter!!.submitList(bookDetailValueList)
        createSimilarList()
    }

    private fun createSimilarList(){
        bookSimilarAdapter = BookItemAdapter()
        bookSimilarAdapter!!.submitList(bookInfo!!.data.similarBooks)
        view.bindInfo()
    }

}