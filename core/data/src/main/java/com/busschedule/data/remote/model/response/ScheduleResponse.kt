package com.busschedule.data.remote.model.response

import com.busschedule.model.Schedule
import com.busschedule.model.constant.TransitConst
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleResponse(
    val id: Int = 0,
    val name: String = "",
    val days: List<String> = emptyList(),
    val startTime: List<Int> = emptyList(),
    val endTime: List<Int> = emptyList(),
    val sections: List<TransitInfoResponse> = emptyList(),
    val destinationType: String = TransitConst.BUS.name,
    val destinationName: String = "",
    val isAlarmOn: Boolean = true
)

fun ScheduleResponse.asDomain() =
    Schedule(
        id = id,
        name = name,
        daysList = days,
        startTime = startTime,
        endTime = endTime,
        sections = sections.map { it.asDomain() },
        destinationName = destinationName,
        destinationType = destinationType,
        isAlarmOn = isAlarmOn
    )