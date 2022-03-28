package com.moon.booklove_android.api

import com.moon.booklove_android.dto.*
import retrofit2.Call
import retrofit2.http.*

interface UserApi {

    // 일반 회원가입
    @POST("api/user")
    fun normalSignUp(@Body normalSignUpReqDTO: NormalSignUpReqDTO): Call<SingleResult<Any>>

    // 일반 로그인
    @GET("api/user")
    fun normalLogin(
        @Query("id") id: String?,
        @Query("password") password: String?): Call<SingleResult<NormalLoginResDTO>>

    // 소셜 로그인
    @POST("api/user/social")
    fun socialSignUp(): Call<SingleResult<SocialLoginResDTO>>

    // 아이디 중복체크 (일반 로그인)
    @GET("api/user/idcheck/{id}")
    fun checkID(@Query("id") id: String?): Call<SingleResult<Any>>

    //회원정보 수정
    @POST("api/user/info")
    fun userInfoUpdate(@Body body: UserInfoUpdateReqDTO): Call<SingleResult<Any>>

    // //회원정보 요청 (일반 로그인)
    @GET("api/user/{userid}")
    fun getUserInfo(@Query("userid") userid: String?): Call<SingleResult<Any>>
}
