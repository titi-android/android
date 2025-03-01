package com.busschedule.domain.repository

import com.busschedule.model.DestinationInfo
import com.busschedule.model.RouteInfo
import com.busschedule.model.ScheduleRegister
import com.busschedule.model.ScheduleTicket

interface ScheduleRepository {
    suspend fun readNowSchedule(): ScheduleTicket?
    suspend fun readTodaySchedules(): List<ScheduleTicket>
    suspend fun readDaySchedules(day: String):List<ScheduleTicket>
    suspend fun readSchedule(scheduleId: Int): ScheduleRegister

    suspend fun postSchedule(
        name: String = "",
        daysList: List<String> = emptyList(),
        startTime: String = "",
        endTime: String = "",
        routeInfos: List<RouteInfo>,
        destinationInfo: DestinationInfo,
        isAlarmOn: Boolean
    )

    suspend fun deleteSchedule(scheduleId: Int)

    suspend fun putSchedule(
        scheduleId: Int,
        name: String = "",
        daysList: List<String> = emptyList(),
        startTime: String = "",
        endTime: String = "",
        routeInfos: List<RouteInfo>,
        destinationInfo: DestinationInfo,
        isAlarmOn: Boolean
    )

    suspend fun putScheduleAlarm(scheduleId: Int)
}