package com.busschedule.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface BusApi {
    @GET("/api/v1/node/bus-names/all")
    suspend fun readAllBusOfBusStop(
        @Query("cityName") cityName: String,
        @Query("nodeId") busStopId: String,
    ): List<String>
    // TODO: 나중에 response json 백엔드에서 바꾸면 사용
}