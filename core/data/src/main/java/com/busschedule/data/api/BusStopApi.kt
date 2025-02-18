package com.busschedule.data.api

import com.busschedule.data.model.DefaultResponse
import com.busschedule.domain.model.response.busstop.BusInfosResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BusStopApi {
    @GET("/api/v1/nodes/infos")
    suspend fun readAllBusStop(
        @Query("cityName") cityName: String,
        @Query("busStopName") busStopName: String,
    ): DefaultResponse<BusInfosResponse>
}