package com.busschedule.data.model.response

import com.busschedule.model.BusStopInfo
import com.busschedule.model.ScheduleTicket
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleTicketResponse(
    val id: Int = 0,
    val name: String = "",
    val daysList: List<String> = emptyList(),
    val startTime: String = "",
    val endTime: String = "",
    val busStopInfos: List<BusStopInfo> = emptyList(),
    val desBusStopName: String,
    val isAlarmOn: Boolean
)

fun ScheduleTicketResponse.asDomain() =
    ScheduleTicket(
        id = id,
        name = name,
        daysList = daysList,
        startTime = startTime,
        endTime = endTime,
        busStopInfos = busStopInfos,
        desBusStopName = desBusStopName,
        isAlarmOn = isAlarmOn
    )