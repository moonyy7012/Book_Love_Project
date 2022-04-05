package com.moon.booklove_android.service

import com.moon.booklove_android.data.dto.*
import com.moon.booklove_android.config.util.RetrofitCallback
import com.moon.booklove_android.config.util.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookService {

    fun getBookListMain(callback: RetrofitCallback<SingleResult<BookMainListResDTO>>) {
        RetrofitUtil.bookService.getBookListMain()
            .enqueue(object : Callback<SingleResult<BookMainListResDTO>> {
                override fun onResponse(call: Call<SingleResult<BookMainListResDTO>>, response: Response<SingleResult<BookMainListResDTO>>) {
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

                override fun onFailure(call: Call<SingleResult<BookMainListResDTO>>, t: Throwable) {
                    callback.onError(t)
                }
            })
    }

    fun getBookListSearch(search: String, callback: RetrofitCallback<ListResult<List<BookListInfoResDTO>>>) {
        RetrofitUtil.bookService.getBookListSearch(search)
            .enqueue(object : Callback<ListResult<List<BookListInfoResDTO>>> {
                override fun onResponse(call: Call<ListResult<List<BookListInfoResDTO>>>, response: Response<ListResult<List<BookListInfoResDTO>>>) {
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

                override fun onFailure(call: Call<ListResult<List<BookListInfoResDTO>>>, t: Throwable) {
                    callback.onError(t)
                }
            })
    }

    fun getBookListCategory(categoryName: String, callback: RetrofitCallback<ListResult<BookListInfoResDTO>>) {
        RetrofitUtil.bookService.getBookListCategory(categoryName)
            .enqueue(object : Callback<ListResult<BookListInfoResDTO>> {
                override fun onResponse(call: Call<ListResult<BookListInfoResDTO>>, response: Response<ListResult<BookListInfoResDTO>>) {
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

                override fun onFailure(call: Call<ListResult<BookListInfoResDTO>>, t: Throwable) {
                    callback.onError(t)
                }
            })
    }

    fun getBookListRecent(callback: RetrofitCallback<SingleResult<BookRecentListResDTO>>) {
        RetrofitUtil.bookService.getBookListRecent()
            .enqueue(object : Callback<SingleResult<BookRecentListResDTO>> {
                override fun onResponse(call: Call<SingleResult<BookRecentListResDTO>>, response: Response<SingleResult<BookRecentListResDTO>>) {
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

                override fun onFailure(call: Call<SingleResult<BookRecentListResDTO>>, t: Throwable) {
                    callback.onError(t)
                }
            })
    }

    fun getBookListRecentSimilar(callback: RetrofitCallback<SingleResult<BookRecentSimilarListResDTO>>) {
        RetrofitUtil.bookService.getBookListRecentSimilar()
            .enqueue(object : Callback<SingleResult<BookRecentSimilarListResDTO>> {
                override fun onResponse(call: Call<SingleResult<BookRecentSimilarListResDTO>>, response: Response<SingleResult<BookRecentSimilarListResDTO>>) {
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

                override fun onFailure(call: Call<SingleResult<BookRecentSimilarListResDTO>>, t: Throwable) {
                    callback.onError(t)
                }
            })
    }

    fun getBookInfo(bookId: Long, callback: RetrofitCallback<SingleResult<BookInfoResDTO>>) {
        RetrofitUtil.bookService.getBookInfo(bookId)
            .enqueue(object : Callback<SingleResult<BookInfoResDTO>> {
                override fun onResponse(call: Call<SingleResult<BookInfoResDTO>>, response: Response<SingleResult<BookInfoResDTO>>) {
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

                override fun onFailure(call: Call<SingleResult<BookInfoResDTO>>, t: Throwable) {
                    callback.onError(t)
                }
            })
    }
}