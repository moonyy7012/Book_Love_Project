package com.moon.booklove_android.api

import com.moon.booklove_android.data.dto.*
import retrofit2.Call
import retrofit2.http.*

interface BookApi {

    // 메인 리스트
    @GET("api/book/bestseller/main")
    fun getBookListMain(): Call<SingleResult<BookMainListResDTO>>

    // 검색 결과 리스트
    @GET("api/book/search/{keyword}")
    fun getBookListSearch(@Path("keyword") search: String): Call<ListResult<List<BookListInfoResDTO>>>

    // 카테고리별 조회 리스트
    @GET("api/book/bestseller")
    fun getBookListCategory(@Query("categoryName") categoryName: String): Call<ListResult<BookListInfoResDTO>>

    // 최근 리스트
    @GET("api/clicklog")
    fun getBookListRecent(): Call<SingleResult<BookRecentListResDTO>>

    // 최근 본 책과 관련된 책 리스트
    @GET("api/clicklog/similar")
    fun getBookListRecentSimilar(): Call<SingleResult<BookRecentSimilarListResDTO>>



    // //책 상세 정보
    @GET("api/book/{bookId}")
    fun getBookInfo(@Path("bookId") bookId: Long): Call<SingleResult<BookInfoResDTO>>

}
