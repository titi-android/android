package com.busschedule.data.remote.api

import com.busschedule.data.remote.model.DefaultResponse
import com.busschedule.data.remote.model.response.SubwayStationLineInfoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SubwayApi {
    @GET("/api/v3/subway/stations")
    suspend fun getSubwayStationLineInfo(@Query("stName") stName: String): DefaultResponse<List<SubwayStationLineInfoResponse>>
}