package com.busschedule.domain.model.response.schedule

import kotlinx.serialization.Serializable

@Serializable
data class ScheduleRegisterResponse(
    val id: Int = 0,
    val name: String = "",
    val days: List<String> = emptyList(),
    val startTime: List<Int> = emptyList(),
    val endTime: List<Int> = emptyList(),
    val regionName: String = "",
    val busStopName: String = "",
    val busNames: List<String> = emptyList(),
    val isAlarmOn: Boolean
)
