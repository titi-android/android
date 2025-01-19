package com.busschedule.data.network

import com.busschedule.data.model.DefaultResponse
import com.busschedule.domain.model.LoginUser
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    // 로그인
    @POST("/api/v1/users/login")
    suspend fun login(@Body user: LoginUser): DefaultResponse<String>

    // 회원가입
    @POST("/api/v1/users/signup")
    suspend fun signup(@Body user: LoginUser): DefaultResponse<Unit>

}