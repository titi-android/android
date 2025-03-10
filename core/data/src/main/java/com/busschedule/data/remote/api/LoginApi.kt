package com.busschedule.data.remote.api

import com.busschedule.data.remote.model.DefaultResponse
import com.busschedule.data.remote.model.request.LoginUserRequest
import com.busschedule.data.remote.model.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    // 로그인
    @POST("/api/v1/users/login")
    suspend fun login(@Body user: LoginUserRequest): DefaultResponse<TokenResponse>

    // 회원 가입
    @POST("/api/v1/users/signup")
    suspend fun signup(@Body user: LoginUserRequest): DefaultResponse<Unit>

}