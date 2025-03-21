package com.busschedule.data.remote.api

import com.busschedule.data.remote.model.DefaultResponse
import com.busschedule.data.remote.model.request.FCMTokenRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface FCMApi {
    @POST("/api/v1/fcm/tokens")
    suspend fun postFCMToken(@Body request: FCMTokenRequest): DefaultResponse<Unit>

    @GET("/api/v1/fcm/test")
    suspend fun sendNotification(): DefaultResponse<Unit>

    @DELETE("/api/v1/fcm/tokens")
    suspend fun deleteFCMToken(): DefaultResponse<Unit>


}