package com.moon.booklove_android.api

import com.moon.booklove_android.dto.*
import retrofit2.Call
import retrofit2.http.*

interface UserApi {

    // 일반 회원가입
    @POST("api/nickname")
    fun normalSignUp(@Body normalSignUpReqDTO: NormalSignUpReqDTO): Call<SingleResult<NormalSignUpResDTO>>

    // 일반 로그인
    @POST("api/nickname")
    fun normalLogin(@Body normalLoginReqDTO: NormalLoginReqDTO): Call<SingleResult<NormalLoginResDTO>>

    // 소셜 로그인
    @GET("api/nickname")
    fun socialSignUp(): Call<SingleResult<SocialLoginResDTO>>

    // 아이디 중복체크 (일반 로그인)
    @GET("api/nickname")
    fun checkID(@Query("userid") userid: String?): Call<SingleResult<CheckIDResDTO>>

    //회원정보 수정
    @PUT("api/nickname")
    fun userInfoUpdate(@Body body: UserInfoUpdateReqDTO): Call<SingleResult<UserInfoUpdateResDTO>>
}
