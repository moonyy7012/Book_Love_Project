package com.moon.booklove_android.api

import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.moon.booklove_android.data.dto.*
import retrofit2.Call
import retrofit2.http.*

interface BookApi {

    // 메인 리스트
    @GET("api/book/bestseller/main")
    fun getBookListMain(): Call<SingleResult<BookMainListResDTO>>


    // 검색 결과 리스트
    @GET("api/user/idcheck/{id}")
    fun getBookListSearch(@Query("search") search: String): Call<SingleResult<BookListSearchResDTO>>

    // 카테고리별 조회 리스트
    @GET("api/book/bestseller")
    fun getBookListCategory(@Query("categoryName") categoryName: String): Call<ListResult<BookListInfoResDTO>>


    // //책 상세 정보
    @GET("api/book/{bookId}")
    fun getBookInfo(@Path("bookId") bookId: Long): Call<SingleResult<BookInfoResDTO>>

}
