package com.busschedule.data.network

import com.busschedule.domain.model.response.busstop.BusInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface BusApi {
    @GET("/api/v1/nodes/bus-names/all")
    suspend fun readAllBusOfBusStop(
        @Query("cityName") cityName: String,
        @Query("nodeId") busStopId: String,
//    ): DefaultResponse<List<BusResponse>>
    ): List<BusInfo>

}