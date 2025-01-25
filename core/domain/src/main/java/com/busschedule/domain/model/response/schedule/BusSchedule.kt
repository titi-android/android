package com.busschedule.domain.model.response.schedule

import kotlinx.serialization.Serializable

@Serializable
data class BusSchedule(
    val id: Int = 0,
    val name: String = "",
    val days: String = "",
    val startTime: List<Int> = emptyList(),
    val endTime: List<Int> = emptyList(),
    val busStopName: String = "",
    val busInfos: List<BusInfo> = emptyList()
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

@Serializable
data class Time(
    val hour: Int = 0,
    val minute: Int = 0
)