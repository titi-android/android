package com.busschedule.register

import com.busschedule.domain.repository.ScheduleRepository
import com.busschedule.model.DestinationInfo
import com.busschedule.model.RouteInfo
import com.busschedule.model.ScheduleRegister
import com.busschedule.model.ScheduleTicket

class FakeScheduleRepository: ScheduleRepository {
    override suspend fun readNowSchedule(): ScheduleTicket? {
        TODO("Not yet implemented")
    }

    override suspend fun readTodaySchedules(): List<ScheduleTicket> {
        TODO("Not yet implemented")
    }

    override suspend fun readDaySchedules(day: String): List<ScheduleTicket> {
        TODO("Not yet implemented")
    }

    override suspend fun readSchedule(scheduleId: Int): ScheduleRegister {
        TODO("Not yet implemented")
    }

    override suspend fun postSchedule(
        name: String,
        daysList: List<String>,
        startTime: String,
        endTime: String,
        routeInfos: List<RouteInfo>,
        destinationInfo: DestinationInfo,
        isAlarmOn: Boolean,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSchedule(scheduleId: Int) {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    override suspend fun putScheduleAlarm(scheduleId: Int) {
        TODO("Not yet implemented")
    }
}