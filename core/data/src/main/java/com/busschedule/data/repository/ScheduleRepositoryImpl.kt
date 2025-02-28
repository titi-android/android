package com.busschedule.data.repository

import com.busschedule.data.api.ScheduleApi
import com.busschedule.data.model.request.ScheduleRegisterRequest
import com.busschedule.data.model.response.asDomain
import com.busschedule.domain.model.response.schedule.BusSchedule
import com.busschedule.domain.repository.ScheduleRepository
import com.busschedule.model.DestinationInfo
import com.busschedule.model.RouteInfo
import com.busschedule.model.ScheduleRegister
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(private val scheduleApi: ScheduleApi) :
    ScheduleRepository {
    override suspend fun readNowSchedule(): BusSchedule? =
        scheduleApi.readNowSchedules().getOrThrow().data

    override suspend fun readTodaySchedules(): List<BusSchedule> =
        scheduleApi.readTodayAllSchedules().getOrThrow().data!!

    override suspend fun readDaySchedules(day: String): List<BusSchedule> =
        scheduleApi.readDaySchedules(day).getOrThrow().data!!

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