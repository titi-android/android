package com.busschedule.data.api

import com.busschedule.data.model.DefaultResponse
import com.busschedule.data.model.request.FCMTokenRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface FCMApi {
    @POST("/api/v1/fcm/tokens")
    suspend fun postFCMToken(@Body request: FCMTokenRequest): DefaultResponse<Unit>
}