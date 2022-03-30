package com.moon.booklove_android.view.main.category.presenter

import android.content.Context
import android.util.Log
import com.moon.booklove_android.adapter.BookItemAdapter
import com.moon.booklove_android.config.ApplicationClass
import com.moon.booklove_android.config.ApplicationClass.Companion.bookCategoryAdapter
import com.moon.booklove_android.config.toast
import com.moon.booklove_android.config.util.RetrofitCallback
import com.moon.booklove_android.data.dto.BookListInfoResDTO
import com.moon.booklove_android.data.dto.ListResult
import com.moon.booklove_android.data.dto.ReissuanceResDTO
import com.moon.booklove_android.data.dto.SingleResult
import com.moon.booklove_android.databinding.FragmentGenreBinding
import com.moon.booklove_android.service.BookService
import com.moon.booklove_android.service.TokenService
import com.moon.booklove_android.view.main.category.CategoryContract

private const val TAG = "CategoryPresenterImpl"

class CategoryPresenterImpl(override var view: CategoryContract.View) : CategoryPresenter {
    var bookList: ListResult<BookListInfoResDTO> ?= null


    override fun getBookListCategory(context: Context, categoryName:String, position:Int) {
        Log.d(TAG, "getBookListCategory: ${categoryName}")
        BookService().getBookListCategory(categoryName, object :
            RetrofitCallback<ListResult<BookListInfoResDTO>> {

            override fun onSuccess(code: Int, responseData: ListResult<BookListInfoResDTO>) {
                Log.d(TAG, "onSuccess: ")
                if (responseData.output==1) {
                    bookList = responseData
                    bookCategoryAdapter = BookItemAdapter()
                    bookCategoryAdapter!!.submitList(bookList!!.data)
                } else {
                    toast("문제가 발생하였습니다. 다시 시도해주세요.",context)
                }
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: ${code}")
                toast("문제가 발생하였습니다. 다시 시도해주세요.",context)
            }

            override fun onError(t: Throwable) {
                Log.d(TAG, "onFailure: ${t}")
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
                            getBookListCategory(context, categoryName, position)
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