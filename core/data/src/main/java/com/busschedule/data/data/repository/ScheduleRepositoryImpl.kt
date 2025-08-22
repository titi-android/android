package com.busschedule.data.data.repository

import com.busschedule.data.remote.api.ScheduleApi
import com.busschedule.data.remote.model.request.ScheduleRegisterRequest
import com.busschedule.data.remote.model.response.asDomain
import com.busschedule.domain.repository.ScheduleRepository
import com.busschedule.model.DestinationInfo
import com.busschedule.model.RouteInfo
import com.busschedule.model.Schedule
import com.busschedule.model.ScheduleRegister
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(private val scheduleApi: ScheduleApi) :
    ScheduleRepository {
    override suspend fun readNowSchedule(): Schedule? =
        scheduleApi.readNowSchedules().getOrThrow().data?.asDomain()

    override suspend fun readTodaySchedules(): List<Schedule> =
        scheduleApi.readTodayAllSchedules().getOrThrow().data?.map { it.asDomain() } ?: emptyList()

    override suspend fun readDaySchedules(day: String): List<Schedule> =
        scheduleApi.readDaySchedules(day).getOrThrow().data?.map { it.asDomain() } ?: emptyList()

    override suspend fun readSchedule(scheduleId: Int): ScheduleRegister =
        scheduleApi.readSchedule(scheduleId).getOrThrow().data!!.asDomain()


    override suspend fun postSchedule(
        name: String,
        daysList: List<String>,
        startTime: String,
        endTime: String,
        routeInfos: List<RouteInfo>,
        destinationInfo: DestinationInfo,
        isAlarmOn: Boolean,
    ) {
        val scheduleRegisterRequest = ScheduleRegisterRequest(
            name = name,
            daysList = daysList,
            startTime = startTime,
            endTime = endTime,
            routeInfos = routeInfos,
            destinationInfo = destinationInfo,
            isAlarmOn = isAlarmOn
        )
        scheduleApi.postSchedule(scheduleRegisterRequest).getOrThrow()
    }

    override suspend fun deleteSchedule(scheduleId: Int) {
        scheduleApi.deleteSchedule(scheduleId).getOrThrow()
    }


    override suspend fun putSchedule(
        scheduleId: Int,
        name: String,
        daysList: List<String>,
        startTime: String,
        endTime: String,
        routeInfos: List<RouteInfo>,
        destinationInfo: DestinationInfo,
        isAlarmOn: Boolean,
    ) {
        val scheduleRegisterRequest = ScheduleRegisterRequest(
            name = name,
            daysList = daysList,
            startTime = startTime,
            endTime = endTime,
            routeInfos = routeInfos,
            destinationInfo = destinationInfo,
            isAlarmOn = isAlarmOn
        )
        scheduleApi.putSchedule(scheduleId = scheduleId, schedule = scheduleRegisterRequest)
            .getOrThrow()
    }

    override suspend fun putScheduleAlarm(scheduleId: Int) {
        scheduleApi.putScheduleAlarm(scheduleId).getOrThrow()
    }

}