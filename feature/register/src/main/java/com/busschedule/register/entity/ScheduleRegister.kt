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
    busList = busStopInfo?.getBuses()?.map { it.name } ?: emptyList(),
    isAlarmOn = isNotify
//    busStopName = busStop,
//    busList = buses.map { it.name },
)

//fun ScheduleRegisterResponse.asEntity() = ScheduleRegister(
//    id = id,
//    name = name,
//    dayOfWeeks = DayOfWeek.entries.map { DayOfWeekUi(dayOfWeek = it, init = days.contains("${it.value}요일")) },
//    startTime = "${startTime[0]}:${startTime[1]}",
//    endTime = "${endTime[0]}:${endTime[1]}",
//    isNotify = isAlarmOn,
//    regionName = regionName,
//    busStopName = busStopName,
//    bus = busNames.map { Bus(it) }
//)