package com.busschedule.model

data class ScheduleRegister(
    val id: Int = 0,
    val name: String = "",
    val days: List<String> = emptyList(),
    val startTime: String = "",
    val endTime: String = "",
    val routeInfos: List<RouteInfo>,
    val destinationInfo: DestinationInfo = DestinationInfo(),
    val isAlarmOn: Boolean
)