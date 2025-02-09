package com.busschedule.data.repository

import com.busschedule.data.network.ScheduleApi
import com.busschedule.domain.model.ApiState
import com.busschedule.domain.model.request.ScheduleRegisterRequest
import com.busschedule.domain.model.response.schedule.BusSchedule
import com.busschedule.domain.model.response.schedule.ScheduleRegisterResponse
import com.busschedule.domain.model.safeFlow
import com.busschedule.domain.model.safeFlowUnit
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

    override fun readSchedule(scheduleId: Int): Flow<ApiState<ScheduleRegisterResponse>> = safeFlow {
        scheduleApi.readSchedule(scheduleId)
    }

    override fun postSchedule(scheduleRegisterRequest: ScheduleRegisterRequest): Flow<ApiState<Unit>> =
        safeFlowUnit { scheduleApi.postSchedule(scheduleRegisterRequest) }

    override fun deleteSchedule(scheduleId: Int): Flow<ApiState<Unit>> = safeFlowUnit {
        scheduleApi.deleteSchedule(scheduleId)
    }

    override fun putScheduleAlarm(scheduleId: Int): Flow<ApiState<Unit>> = safeFlowUnit {
        scheduleApi.putScheduleAlarm(scheduleId)
    }

}