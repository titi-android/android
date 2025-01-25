package com.busschedule.register.entity

import com.busschedule.domain.model.request.ScheduleRegisterRequest

data class ScheduleRegister (
    val name: String = "",
    val days: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val regionName: String = "",
    val busStopName: String = "",
    val busStopSupportingName: SupportingBusStopText = SupportingBusStopText(),
    val bus: String = "",
//    val busList: List<String> = emptyList()
)

fun ScheduleRegister.asDomain() = ScheduleRegisterRequest(
    name = name,
    days = days,
    startTime = startTime,
    endTime = endTime,
    regionName = regionName,
    busStopName = busStopName,
    busList = listOf(bus)
//    busList = busList
)