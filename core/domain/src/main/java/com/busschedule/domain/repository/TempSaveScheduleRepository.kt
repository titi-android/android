package com.busschedule.domain.repository

import com.busschedule.model.BusStop
import com.busschedule.model.RouteInfo
import com.busschedule.model.ScheduleRegister

interface TempSaveScheduleRepository {
    suspend fun insert(
        name: String = "",
        dayOfWeeks: List<String> = emptyList(),
        startTime: String = "",
        endTime: String = "",
        isNotify: Boolean = false,
        routeInfos: List<RouteInfo> = emptyList(),
        arriveBusStop: BusStop = BusStop(),
    )

    suspend fun read(): ScheduleRegister

    suspend fun delete(): Boolean

    suspend fun isExist(): Boolean

}