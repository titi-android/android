package com.busschedule.register

import com.busschedule.domain.repository.ScheduleRepository
import com.busschedule.model.DestinationInfo
import com.busschedule.model.BusRegister
import com.busschedule.model.ScheduleRegister
import com.busschedule.model.Schedule

class FakeScheduleRepository: ScheduleRepository {
    override suspend fun readNowSchedule(): Schedule? {
        TODO("Not yet implemented")
    }

    override suspend fun readTodaySchedules(): List<Schedule> {
        TODO("Not yet implemented")
    }

    override suspend fun readDaySchedules(day: String): List<Schedule> {
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
        busRegisters: List<BusRegister>,
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
        busRegisters: List<BusRegister>,
        destinationInfo: DestinationInfo,
        isAlarmOn: Boolean,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun putScheduleAlarm(scheduleId: Int) {
        TODO("Not yet implemented")
    }
}