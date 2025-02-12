package com.busschedule.register.entity

import com.busschedule.domain.model.request.ScheduleRegisterRequest
import com.busschedule.util.entity.DayOfWeekUi

data class ScheduleRegister (
    val name: String = "",
    val dayOfWeeks: List<DayOfWeekUi> = emptyList(),
    val startTime: String = "",
    val endTime: String = "",
    val isNotify: Boolean = false,
    val regionName: String = "",
    val busStopInfo: BusStopInfo? = null
)

fun ScheduleRegister.asDomain() = ScheduleRegisterRequest(
    name = name,
    daysList = dayOfWeeks.filter { it.isSelected }.map { "${it.dayOfWeek.value}요일" },
    startTime = startTime,
    endTime = endTime,
    regionName = regionName,
    busStopName = busStopInfo?.busStop ?: "",
    nodeId = busStopInfo?.nodeId ?: "",
    busInfos = busStopInfo?.getBuses() ?: emptyList(),
    isAlarmOn = isNotify
)