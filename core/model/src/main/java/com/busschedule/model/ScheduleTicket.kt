package com.busschedule.model

import kotlinx.serialization.Serializable

@Serializable
data class ScheduleTicket(
    val id: Int = 0,
    val name: String = "",
    val daysList: List<String> = emptyList(),
    val startTime: String = "",
    val endTime: String = "",
    val busStopInfos: List<BusStopInfo> = emptyList(),
    val desBusStopName: String,
    val isAlarmOn: Boolean
)

@Serializable
data class BusStopInfo(
    val busStopName: String = "",
    val busInfos: List<ArrivingBus> = emptyList(),
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