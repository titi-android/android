package com.busschedule.register.entity

import com.busschedule.domain.model.response.schedule.Time

data class ScheduleRegister (
    val name: String = "",
    val days: String = "",
    val startTime: Time = Time(),
    val endTime: Time = Time(),
    val regionName: String = "",
    val busStopName: String = "",
    val busStopSupportingName: SupportingBusStopText = SupportingBusStopText(),
    val busList: List<String> = emptyList()
)