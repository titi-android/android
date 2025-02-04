package com.busschedule.data.network

import com.busschedule.data.model.DefaultResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BusApi {
    @GET("/api/v1/nodes/bus-names/all")
    suspend fun readAllBusOfBusStop(
        @Query("cityName") cityName: String,
        @Query("nodeId") busStopId: String,
    ): DefaultResponse<List<String>>
    // TODO: 나중에 response json 백엔드에서 바꾸면 사용, 지금 객체 형태가 아닌 리스트만 받아옴 List<String>
}