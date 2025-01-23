package com.busschedule.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class ScheduleRegisterRequest(
    val name: String = "",
    val days: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val regionName: String = "",
    val busStopName: String = "",
    val busList: List<String> = emptyList()
)