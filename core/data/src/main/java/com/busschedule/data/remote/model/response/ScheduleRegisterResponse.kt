package com.busschedule.data.remote.model.response

import com.busschedule.model.DestinationInfo
import com.busschedule.model.RouteInfo
import com.busschedule.model.ScheduleRegister
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleRegisterResponse(
    val id: Int = 0,
    val name: String = "",
    val daysList: List<String> = emptyList(),
    val startTime: String = "",
    val endTime: String = "",
    val routeInfos: List<RouteInfo>,
    val destinationInfo: DestinationInfo,
    val isAlarmOn: Boolean
)

fun ScheduleRegisterResponse.asDomain() = ScheduleRegister(
    id = id,
    name = name,
    days = daysList,
    startTime = startTime,
    endTime = endTime,
    routeInfos = routeInfos,
    destinationInfo = destinationInfo,
    isAlarmOn = isAlarmOn
)