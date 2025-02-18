package com.busschedule.data.repository

import com.busschedule.data.api.ScheduleApi
import com.busschedule.domain.repository.ScheduleRepository
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(private val scheduleApi: ScheduleApi) :
    ScheduleRepository {
//    override fun readNowSchedule(): Flow<ApiResult<BusSchedule>> = safeFlow {
//        scheduleApi.readNowSchedules()
//    }
//
//    override fun raedTodaySchedules(): Flow<ApiResult<List<BusSchedule>>> = safeFlow {
//        scheduleApi.readTodayAllSchedules()
//    }
//
//    override fun raedDaySchedules(day: String): Flow<ApiResult<List<BusSchedule>>> = safeFlow {
//        scheduleApi.readDaySchedules(day)
//    }
//
//    override fun readSchedule(scheduleId: Int): Flow<ApiResult<ScheduleRegisterResponse>> = safeFlow {
//        scheduleApi.readSchedule(scheduleId)
//    }
//
//    override fun postSchedule(scheduleRegisterRequest: ScheduleRegisterRequest): Flow<ApiResult<Unit>> =
//        safeFlowUnit { scheduleApi.postSchedule(scheduleRegisterRequest) }
//
//    override fun deleteSchedule(scheduleId: Int): Flow<ApiResult<Unit>> = safeFlowUnit {
//        scheduleApi.deleteSchedule(scheduleId)
//    }
//
//    override fun putSchedule(scheduleId: Int, schedule: ScheduleRegisterRequest): Flow<ApiResult<Unit>> = safeFlowUnit {
//        scheduleApi.putSchedule(scheduleId = scheduleId, schedule = schedule)
//    }
//
//    override fun putScheduleAlarm(scheduleId: Int): Flow<ApiResult<Unit>> = safeFlowUnit {
//        scheduleApi.putScheduleAlarm(scheduleId)
//    }

}