package com.busschedule.data.network

import com.busschedule.data.model.DefaultResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ScheduleApi {
    // 스케줄 목록 조회
    @GET("/api/v2/schedules/days")
    fun readAllSchedules(@Query("days") days: String): DefaultResponse<String>
}