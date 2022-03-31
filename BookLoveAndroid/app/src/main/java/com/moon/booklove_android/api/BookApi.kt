package com.moon.booklove_android.api

import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import com.moon.booklove_android.data.dto.*
import retrofit2.Call
import retrofit2.http.*

interface BookApi {

    // 메인 리스트
    @GET("api/user")
    fun getBookListMain(): Call<SingleResult<BookListMainResDTO>>

    // 검색 결과 리스트
    @GET("api/book/search/{keyword}")
    fun getBookListSearch(@Path("keyword") search: String): Call<ListResult<List<BookListInfoResDTO>>>

    // 카테고리별 조회 리스트
    @GET("api/book/bestseller")
    fun getBookListCategory(@Query("categoryName") categoryName: String): Call<ListResult<BookListInfoResDTO>>

    // 책 상세 정보 비슷한 책 리스트
    @GET("api/user/idcheck/{id}")
    fun getBookListSimilar(@Query("bookId") bookId: String): Call<SingleResult<BookListCategoryResDTO>>

    // //책 상세 정보
    @GET("api/book/{bookId}")
    fun getBookInfo(@Path("bookId") bookId: Long): Call<SingleResult<BookInfoResDTO>>

}
