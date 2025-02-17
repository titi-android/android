package com.busschedule.domain.repository

import com.busschedule.domain.model.ApiState
import com.busschedule.domain.model.request.ScheduleRegisterRequest
import com.busschedule.domain.model.response.schedule.BusSchedule
import com.busschedule.domain.model.response.schedule.ScheduleRegisterResponse
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun readNowSchedule(): Flow<ApiState<BusSchedule>>
    fun raedTodaySchedules(): Flow<ApiState<List<BusSchedule>>>
    fun raedDaySchedules(day: String): Flow<ApiState<List<BusSchedule>>>
    fun readSchedule(scheduleId: Int): Flow<ApiState<ScheduleRegisterResponse>>

    fun postSchedule(scheduleRegisterRequest: ScheduleRegisterRequest): Flow<ApiState<Unit>>

    fun deleteSchedule(scheduleId: Int): Flow<ApiState<Unit>>

    fun putSchedule(scheduleId: Int, schedule: ScheduleRegisterRequest): Flow<ApiState<Unit>>
    fun putScheduleAlarm(scheduleId: Int): Flow<ApiState<Unit>>

}