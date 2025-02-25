package com.busschedule.data.api

import com.busschedule.data.model.DefaultResponse
import com.busschedule.data.model.request.InquiryRequest
import com.busschedule.data.model.request.LoginUserRequest
import com.busschedule.data.model.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface UserApi {

    // 로그인
    @POST("/api/v1/users/login")
    suspend fun login(@Body user: LoginUserRequest): DefaultResponse<TokenResponse>

    // 회원 가입
    @POST("/api/v1/users/signup")
    suspend fun signup(@Body user: LoginUserRequest): DefaultResponse<Unit>

    // 회원 탈퇴
    @DELETE("/api/v1/users")
    suspend fun signup(@Body user: LoginUserRequest): DefaultResponse<Unit>

    // 문의 사항 이메일 전송
    @POST("/api/v1/users/feed-back")
    suspend fun postInquiry(@Body inquiry: InquiryRequest): DefaultResponse<Unit>


}