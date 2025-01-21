package com.busschedule.domain.model.request

import com.busschedule.domain.model.response.schedule.Time
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleRegister(
    val name: String = "",
    val days: String = "",
    val startTime: Time = Time(),
    val endTime: Time = Time(),
    val regionName: String = "",
    val busStopName: String = "",
    val busList: List<String> = emptyList()
)