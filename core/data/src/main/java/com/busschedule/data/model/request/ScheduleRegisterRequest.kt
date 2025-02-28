package com.busschedule.data.model.request

import com.busschedule.model.DestinationInfo
import com.busschedule.model.RouteInfo
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleRegisterRequest(
    val name: String = "",
    val daysList: List<String> = emptyList(),
    val startTime: String = "",
    val endTime: String = "",
    val routeInfos: List<RouteInfo>,
    val destinationInfo: DestinationInfo,
    val isAlarmOn: Boolean
)



