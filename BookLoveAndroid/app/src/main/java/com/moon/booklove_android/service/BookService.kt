package com.moon.booklove_android.service

import android.util.Log
import com.moon.booklove_android.data.dto.*
import com.moon.booklove_android.config.util.RetrofitCallback
import com.moon.booklove_android.config.util.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "BookService"

class BookService {

    fun getBookListMain(callback: RetrofitCallback<SingleResult<BookListMainResDTO>>) {
        RetrofitUtil.bookService.getBookListMain()
            .enqueue(object : Callback<SingleResult<BookListMainResDTO>> {
                override fun onResponse(call: Call<SingleResult<BookListMainResDTO>>, response: Response<SingleResult<BookListMainResDTO>>) {
                    val res = response.body()
                    if (response.code() == 200) {
                        if (res != null) {
                            callback.onSuccess(response.code(), res)
                        }
                    } else if(response.code() == 403){
                        callback.onExpired(response.code())
                    } else{
                        callback.onFailure(response.code())
                    }
                }

                override fun onFailure(call: Call<SingleResult<BookListMainResDTO>>, t: Throwable) {
                    callback.onError(t)
                }
            })
    }

    fun getBookListSearch(search: String, callback: RetrofitCallback<SingleResult<BookListSearchResDTO>>) {
        RetrofitUtil.bookService.getBookListSearch(search)
            .enqueue(object : Callback<SingleResult<BookListSearchResDTO>> {
                override fun onResponse(call: Call<SingleResult<BookListSearchResDTO>>, response: Response<SingleResult<BookListSearchResDTO>>) {
                    val res = response.body()
                    if (response.code() == 200) {
                        if (res != null) {
                            callback.onSuccess(response.code(), res)
                        }
                    } else if(response.code() == 403){
                        callback.onExpired(response.code())
                    } else{
                        callback.onFailure(response.code())
                    }
                }

                override fun onFailure(call: Call<SingleResult<BookListSearchResDTO>>, t: Throwable) {
                    callback.onError(t)
                }
            })
    }

    fun getBookListCategory(categoryName: String, callback: RetrofitCallback<ListResult<BookListInfoResDTO>>) {
        RetrofitUtil.bookService.getBookListCategory(categoryName)
            .enqueue(object : Callback<ListResult<BookListInfoResDTO>> {
                override fun onResponse(call: Call<ListResult<BookListInfoResDTO>>, response: Response<ListResult<BookListInfoResDTO>>) {
                    val res = response.body()
                    Log.d(TAG, "onResponse: ")
//                    Log.d(TAG, "onResponse: ${res!!.data[0].title}")
                    if (response.code() == 200) {
                        if (res != null) {
                            Log.d(TAG, "onResponse1: ")
                            callback.onSuccess(response.code(), res)
                        }else{
                            Log.d(TAG, "onResponse2: ")
                        }
                    } else if(response.code() == 403){
                        Log.d(TAG, "onResponse403: ")
                        callback.onExpired(response.code())
                    } else{
                        Log.d(TAG, "onResponse: ${response.code()}")
                        callback.onFailure(response.code())
                    }
                }

                override fun onFailure(call: Call<ListResult<BookListInfoResDTO>>, t: Throwable) {
                    Log.d(TAG, "onFailure: ")
                    callback.onError(t)
                }
            })
    }

    fun getBookListSimilar(bookId: String, callback: RetrofitCallback<SingleResult<BookListCategoryResDTO>>) {
        RetrofitUtil.bookService.getBookListSimilar(bookId)
            .enqueue(object : Callback<SingleResult<BookListCategoryResDTO>> {
                override fun onResponse(call: Call<SingleResult<BookListCategoryResDTO>>, response: Response<SingleResult<BookListCategoryResDTO>>) {
                    val res = response.body()
                    if (response.code() == 200) {
                        if (res != null) {
                            callback.onSuccess(response.code(), res)
                        }
                    } else if(response.code() == 403){
                        callback.onExpired(response.code())
                    } else{
                        callback.onFailure(response.code())
                    }
                }

                override fun onFailure(call: Call<SingleResult<BookListCategoryResDTO>>, t: Throwable) {
                    callback.onError(t)
                }
            })
    }

    fun getBookInfo(bookId: String, callback: RetrofitCallback<SingleResult<BookListCategoryResDTO>>) {
        RetrofitUtil.bookService.getBookInfo(bookId)
            .enqueue(object : Callback<SingleResult<BookListCategoryResDTO>> {
                override fun onResponse(call: Call<SingleResult<BookListCategoryResDTO>>, response: Response<SingleResult<BookListCategoryResDTO>>) {
                    val res = response.body()
                    if (response.code() == 200) {
                        if (res != null) {
                            callback.onSuccess(response.code(), res)
                        }
                    } else if(response.code() == 403){
                        callback.onExpired(response.code())
                    } else{
                        callback.onFailure(response.code())
                    }
                }

                override fun onFailure(call: Call<SingleResult<BookListCategoryResDTO>>, t: Throwable) {
                    callback.onError(t)
                }
            })
    }
}
