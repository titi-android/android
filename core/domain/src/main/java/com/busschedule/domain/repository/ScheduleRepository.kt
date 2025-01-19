package com.busschedule.domain.repository

import com.busschedule.domain.model.ApiState
import com.busschedule.domain.model.schedule.BusSchedule
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun raedTodaySchedules(): Flow<ApiState<List<BusSchedule>>>
    fun raedDaySchedules(day: String): Flow<ApiState<List<BusSchedule>>>

}