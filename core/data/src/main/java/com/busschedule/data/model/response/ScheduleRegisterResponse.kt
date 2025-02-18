package com.busschedule.data.model.response

import com.busschedule.model.BusInfo
import com.busschedule.model.ScheduleRegister
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleRegisterResponse(
    val id: Int = 0,
    val name: String = "",
    val days: List<String> = emptyList(),
    val startTime: String = "",
    val endTime: String = "",
    val regionName: String = "",
    val busStopName: String = "",
    val nodeId: String = "",
    val busInfos: List<BusInfo> = emptyList(),
    val isAlarmOn: Boolean
)

fun ScheduleRegisterResponse.asDomain() = ScheduleRegister(
    id = id,
    name = name,
    days = days,
    startTime = startTime,
    endTime = endTime,
    regionName = regionName,
    busStopName = busStopName,
    nodeId = nodeId,
    busInfos = busInfos,
    isAlarmOn = isAlarmOn
)