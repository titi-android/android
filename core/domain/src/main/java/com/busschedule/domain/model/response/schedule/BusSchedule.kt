package com.busschedule.domain.model.response.schedule

import kotlinx.serialization.Serializable

@Serializable
data class BusSchedule(
    val id: Int = 0,
    val name: String = "",
    val days: List<String> = emptyList(),
    val startTime: List<Int> = emptyList(),
    val endTime: List<Int> = emptyList(),
    val busStopName: String = "",
    val busInfos: List<BusInfo> = emptyList(),
    val isAlarmOn: Boolean
)

@Serializable
data class BusInfo(
    val arrprevstationcnt: Int = 0,
    val arrtime: Int = 0,
    val nodeid: String = "",
    val nodenm: String = "",
    val routeid: String = "",
    val routeno: String = "",
    val routetp: String = "",
    val vehicletp: String = ""
)