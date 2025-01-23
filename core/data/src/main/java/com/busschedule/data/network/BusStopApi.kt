package com.busschedule.data.network

import com.busschedule.data.model.DefaultResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BusStopApi {
    @GET("/api/v1/node/id")
    suspend fun checkBusStopId(
        @Query("cityName") cityName: String,
        @Query("name") busName: String,
    ): DefaultResponse<String>


}