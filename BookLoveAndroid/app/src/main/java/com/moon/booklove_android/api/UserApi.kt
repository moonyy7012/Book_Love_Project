package com.moon.booklove_android.api

import com.moon.booklove_android.data.dto.*
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
        @Query("password") password: String?): Call<SingleResult<LoginResDTO>>

    // 소셜 로그인
    @POST("api/user/social")
    fun socialSignUp(): Call<SingleResult<LoginResDTO>>

    // 아이디 중복체크 (일반 로그인)
    @GET("api/user/idcheck/{id}")
    fun checkID(@Query("id") id: String?): Call<SingleResult<Any>>

    //회원정보 입력 받기
    @PATCH("api/user/info")
    fun userInputInfo(@Body body: UserInputInfoReqDTO): Call<SingleResult<Any>>

    //닉네임 수정
    @PATCH("api/user/nickname")
    fun userUpdateNickName(@Body body: UpdateNicknameReqDTO): Call<SingleResult<Any>>

    //비밀번호 수정
    @PATCH("api/user/password")
    fun userUpdatePassword(@Body body: UpdatePasswordReqDTO): Call<SingleResult<Any>>

    // //회원정보 요청 (일반 로그인)
    @GET("api/user/auto")
    fun autoLogin(): Call<SingleResult<LoginResDTO>>
}
