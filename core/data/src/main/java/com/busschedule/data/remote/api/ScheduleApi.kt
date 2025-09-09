package com.busschedule.data.remote.api

import com.busschedule.data.remote.model.DefaultResponse
import com.busschedule.data.remote.model.request.ScheduleRegisterRequest
import com.busschedule.data.remote.model.response.ScheduleRegisterResponse
import com.busschedule.data.remote.model.response.ScheduleResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ScheduleApi {
    // 현재 스케줄의 가장 빠른 첫 번쨰, 두 번째 버스 정보 조회
    @GET("/api/v3/schedules/now")
    suspend fun readNowSchedules(): DefaultResponse<ScheduleResponse>
    // 오늘 스케줄 목록 조회
    @GET("/api/v3/schedules/today")
    suspend fun readTodayAllSchedules(): DefaultResponse<List<ScheduleResponse>>

    // 해당 요일 스케줄 목록 조회
    @GET("/api/v3/schedules/days")
    suspend fun readDaySchedules(@Query("days") days: String): DefaultResponse<List<ScheduleResponse>>

    // 스케줄 ID로 스케줄 조회
    @GET("/api/v1/schedules/{scheduleId}")
    suspend fun readSchedule(@Path("scheduleId") scheduleId: Int): DefaultResponse<ScheduleRegisterResponse>

    // 스케줄 등록
    @POST("/api/v2/schedules")
    suspend fun postSchedule(@Body schedule: ScheduleRegisterRequest): DefaultResponse<Unit>

    // 스케줄 삭제
    @DELETE("/api/v1/schedules/{scheduleId}")
    suspend fun deleteSchedule(@Path("scheduleId") scheduleId: Int): DefaultResponse<Unit>

    // 스케줄 수정
    @PUT("/api/v3/schedules/{scheduleId}")
    suspend fun putSchedule(@Path("scheduleId") scheduleId: Int, @Body schedule: ScheduleRegisterRequest): DefaultResponse<Unit>

    // 스케줄 알림 수정
    @PUT("/api/v1/schedules/alarm/{scheduleId}")
    suspend fun putScheduleAlarm(@Path("scheduleId") scheduleId: Int): DefaultResponse<Unit>
}