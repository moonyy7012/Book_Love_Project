package com.moon.booklove_android.view.main.search.presenter

import android.content.Context
import com.moon.booklove_android.adapter.BookCategoryAdapter
import com.moon.booklove_android.config.ApplicationClass
import com.moon.booklove_android.config.ApplicationClass.Companion.bookListAdapter
import com.moon.booklove_android.config.toast
import com.moon.booklove_android.config.util.RetrofitCallback
import com.moon.booklove_android.data.dto.*
import com.moon.booklove_android.service.BookService
import com.moon.booklove_android.service.TokenService
import com.moon.booklove_android.view.main.search.SearchContract

class SearchPresenterImpl(override var view: SearchContract.View) : SearchPresenter {

    override fun getBookListSearch(context: Context, search:String) {

        BookService().getBookListSearch(search, object :
            RetrofitCallback<ListResult<List<BookListInfoResDTO>>> {

            override fun onSuccess(code: Int, responseData: ListResult<List<BookListInfoResDTO>>) {
                if (responseData.output==1) {
                    val bookTitleList = responseData.data[0]
                    val bookAuthorList = responseData.data[1]

                    var bookSearch = mutableListOf<BookCategory>()
                    bookSearch += BookCategory(1, ApplicationClass.search[0], bookTitleList)
                    bookSearch += BookCategory(2, ApplicationClass.search[1], bookAuthorList)

                    bookListAdapter = BookCategoryAdapter()
                    bookListAdapter!!.submitList(bookSearch)
                    view.connectAdapter()
                } else {
                    toast("문제가 발생하였습니다. 다시 시도해주세요.",context)
                }
            }

            override fun onFailure(code: Int) { toast("문제가 발생하였습니다. 다시 시도해주세요.",context) }

            override fun onError(t: Throwable) { toast("문제가 발생하였습니다. 다시 시도해주세요.",context) }

            override fun onExpired(code: Int) {
                TokenService().reissuance(object :
                    RetrofitCallback<SingleResult<ReissuanceResDTO>> {
                    override fun onSuccess(code: Int, responseData: SingleResult<ReissuanceResDTO>) {
                        if (responseData.output==1) {
                            ApplicationClass.prefs.setJWTAccess(responseData.data.accessToken)
                            ApplicationClass.prefs.setJWTRefresh(responseData.data.refreshToken)
                            ApplicationClass.initRetrofit()
                            getBookListSearch(context, search)
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