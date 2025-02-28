package com.busschedule.model

data class ScheduleRegister(
    val id: Int = 0,
    val name: String = "",
    val days: List<String> = emptyList(),
    val startTime: String = "",
    val endTime: String = "",
    val busStops: List<RouteInfo>,
    val destinationInfo: DestinationInfo,
    val isAlarmOn: Boolean
)