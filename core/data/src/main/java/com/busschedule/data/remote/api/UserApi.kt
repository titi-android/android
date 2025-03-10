package com.busschedule.data.remote.api

import com.busschedule.data.remote.model.DefaultResponse
import com.busschedule.data.remote.model.request.InquiryRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface UserApi {
    // 회원 탈퇴
    @DELETE("/api/v1/users")
    suspend fun delete(): DefaultResponse<Unit>

    // 문의 사항 이메일 전송
    @POST("/api/v1/users/feed-back")
    suspend fun postInquiry(@Body inquiry: InquiryRequest): DefaultResponse<Unit>
}