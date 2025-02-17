package com.busschedule.domain.model.response.schedule

import kotlinx.serialization.Serializable

@Serializable
data class BusSchedule(
    val id: Int = 0,
    val name: String = "",
    val daysList: List<String> = emptyList(),
    val startTime: String = "",
    val endTime: String = "",
    val busStopName: String = "",
    val busInfos: List<ArrivingBus> = emptyList(),
    val isAlarmOn: Boolean
)

@Serializable
data class ArrivingBus(
    val arrprevstationcnt: Int = 0,
    val arrtime: Int = 0,
    val nodeid: String = "",
    val nodenm: String = "",
    val routeid: String = "",
    val routeno: String = "",
    val routetp: String = "",
    val vehicletp: String = ""
)