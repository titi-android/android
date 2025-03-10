package com.busschedule.data.remote.api

import com.busschedule.data.remote.model.DefaultResponse
import com.busschedule.data.remote.model.request.FCMTokenRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface FCMApi {
    @POST("/api/v1/fcm/tokens")
    suspend fun postFCMToken(@Body request: FCMTokenRequest): DefaultResponse<Unit>


}