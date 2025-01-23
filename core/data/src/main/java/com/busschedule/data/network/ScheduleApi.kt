package com.busschedule.data.network

import com.busschedule.data.model.DefaultResponse
import com.busschedule.domain.model.request.ScheduleRegisterRequest
import com.busschedule.domain.model.response.schedule.BusSchedule
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ScheduleApi {
    // 오늘 스케줄 목록 조회
    @GET("/api/v2/schedules/today")
    suspend fun readTodayAllSchedules(): DefaultResponse<List<BusSchedule>>

    // 해당 요일 스케줄 목록 조회
    @GET("/api/v2/schedules/days")
    suspend fun readDaySchedules(@Query("days") days: String): DefaultResponse<List<BusSchedule>>

    // 스케줄 등록
    @POST("/api/v1/schedules")
    suspend fun postSchedule(@Body schedule: ScheduleRegisterRequest): DefaultResponse<Unit>
}