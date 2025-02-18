package com.busschedule.data.api

import com.busschedule.data.network.ApiResult
import com.busschedule.model.BusInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface BusApi {
    @GET("/api/v1/nodes/bus-names/all")
    suspend fun readAllBusOfBusStop(
        @Query("cityName") cityName: String,
        @Query("nodeId") busStopId: String,
    ): ApiResult<List<BusInfo>>
}