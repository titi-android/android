package com.busschedule.schedulelist.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.busschedule.domain.model.response.schedule.BusInfo
import com.busschedule.domain.model.response.schedule.BusSchedule

data class BusScheduleUi(
    val id: Int = 0,
    val name: String = "",
    val days: List<String> = emptyList(),
    val startTime: List<Int> = emptyList(),
    val endTime: List<Int> = emptyList(),
    val busStopName: String = "",
    val busInfos: List<BusInfo> = emptyList(),
    private val alarmInit: Boolean
) {
    private var isCheckedAlarm by mutableStateOf(alarmInit)

    fun updateAlarm() {
        isCheckedAlarm = !isCheckedAlarm
    }

    fun getAlarm() = isCheckedAlarm
}

fun BusSchedule.asDomain() =
    BusScheduleUi(
        id = id,
        name = name,
        days = days,
        startTime = startTime,
        endTime = endTime,
        busStopName = busStopName,
        busInfos = busInfos,
        alarmInit = isAlarmOn
    )