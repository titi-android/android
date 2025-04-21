package com.busschedule.register

import com.busschedule.domain.repository.TempSaveScheduleRepository
import com.busschedule.model.BusStop
import com.busschedule.model.RouteInfo
import com.busschedule.model.ScheduleRegister

class FakeTempSaveScheduleRepository: TempSaveScheduleRepository {
    override suspend fun insert(
        name: String,
        dayOfWeeks: List<String>,
        startTime: String,
        endTime: String,
        isNotify: Boolean,
        routeInfos: List<RouteInfo>,
        arriveBusStop: BusStop,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun read(): ScheduleRegister {
        TODO("Not yet implemented")
    }

    override suspend fun delete(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun isExist(): Boolean {
        TODO("Not yet implemented")
    }
}