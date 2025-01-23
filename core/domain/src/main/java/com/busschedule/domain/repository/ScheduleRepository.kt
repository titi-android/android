package com.busschedule.domain.repository

import com.busschedule.domain.model.ApiState
import com.busschedule.domain.model.request.ScheduleRegisterRequest
import com.busschedule.domain.model.response.schedule.BusSchedule
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun raedTodaySchedules(): Flow<ApiState<List<BusSchedule>>>
    fun raedDaySchedules(day: String): Flow<ApiState<List<BusSchedule>>>

    fun postSchedule(scheduleRegisterRequest: ScheduleRegisterRequest): Flow<ApiState<Unit>>

}