package com.busschedule.data.remote.model.response

import com.busschedule.model.DestinationInfo
import com.busschedule.model.BusRegister
import com.busschedule.model.ScheduleRegister
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleRegisterResponse(
    val id: Int = 0,
    val name: String = "",
    val days: List<String> = emptyList(),
    val startTime: String = "",
    val endTime: String = "",
    val busStops: List<BusRegister>,
    val destinationInfo: DestinationInfo,
    val isAlarmOn: Boolean
)

fun ScheduleRegisterResponse.asDomain() = ScheduleRegister(
    id = id,
    name = name,
    days = days,
    startTime = startTime,
    endTime = endTime,
    busStops = busStops,
    destinationInfo = destinationInfo,
    isAlarmOn = isAlarmOn
)