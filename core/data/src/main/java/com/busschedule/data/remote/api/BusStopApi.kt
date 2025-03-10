package com.busschedule.data.remote.api

import com.busschedule.data.remote.model.DefaultResponse
import com.busschedule.data.remote.model.response.BusInfosResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BusStopApi {
    @GET("/api/v1/nodes/infos")
    suspend fun readAllBusStop(
        @Query("cityName") cityName: String,
        @Query("busStopName") busStopName: String,
    ): DefaultResponse<BusInfosResponse>
}