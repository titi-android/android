package com.busschedule.data.api

import com.busschedule.data.model.DefaultResponse
import com.busschedule.data.model.request.LoginUserRequest
import com.busschedule.data.model.response.TokenResponse
import com.busschedule.data.network.ApiResult
import com.busschedule.domain.model.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    // 로그인
    @POST("/api/v1/users/login")
    suspend fun login(@Body user: LoginUserRequest): ApiResult<ApiResponse<TokenResponse>>

    // 회원가입
    @POST("/api/v1/users/signup")
    suspend fun signup(@Body user: LoginUserRequest): DefaultResponse<Unit>

}