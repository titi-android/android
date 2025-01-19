package com.busschedule.data.repository

import com.busschedule.data.network.ScheduleApi
import com.busschedule.domain.model.ApiState
import com.busschedule.domain.model.safeFlow
import com.busschedule.domain.model.schedule.BusSchedule
import com.busschedule.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(private val scheduleApi: ScheduleApi) :
    ScheduleRepository {
    override fun raedTodaySchedules(): Flow<ApiState<List<BusSchedule>>> = safeFlow {
        scheduleApi.readTodayAllSchedules()
    }

    override fun raedDaySchedules(day: String): Flow<ApiState<List<BusSchedule>>> = safeFlow {
        scheduleApi.readDaySchedules(day)
    }

}