package com.busschedule.schedulelist.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.busschedule.domain.model.response.schedule.ArrivingBus
import com.busschedule.domain.model.response.schedule.BusSchedule

data class BusScheduleUi(
    val id: Int = 0,
    val name: String = "",
    val days: List<String> = emptyList(),
    val startTime: String = "",
    val endTime: String = "",
    val busStopName: String = "",
    val busInfos: List<ArrivingBus> = emptyList(),
    private val alarmInit: Boolean = false
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
        days = daysList,
        startTime = startTime,
        endTime = endTime,
        busStopName = busStopName,
        busInfos = busInfos,
        alarmInit = isAlarmOn
    )