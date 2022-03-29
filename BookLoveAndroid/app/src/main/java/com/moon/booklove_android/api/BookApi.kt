package com.moon.booklove_android.api

import com.moon.booklove_android.data.dto.*
import retrofit2.Call
import retrofit2.http.*

interface BookApi {

    // 메인 리스트
    @GET("api/user")
    fun getBookListMain(): Call<SingleResult<BookListMainResDTO>>

    // 검색 결과 리스트
    @GET("api/user/idcheck/{id}")
    fun getBookListSearch(@Query("search") search: String): Call<SingleResult<BookListSearchResDTO>>

    // 장르 리스트
    @GET("api/user/idcheck/{id}")
    fun getBookListCategory(@Query("category") category: String): Call<SingleResult<BookListCategoryResDTO>>

    // 책 상세 정보 비슷한 책 리스트
    @GET("api/user/idcheck/{id}")
    fun getBookListSimilar(@Query("bookId") bookId: String): Call<SingleResult<BookListCategoryResDTO>>

    // //책 상세 정보
    @GET("api/user/idcheck/{id}")
    fun getBookInfo(@Query("bookId") bookId: String): Call<SingleResult<BookListCategoryResDTO>>

}
