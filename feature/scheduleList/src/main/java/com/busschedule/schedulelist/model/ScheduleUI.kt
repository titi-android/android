package com.busschedule.schedulelist.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.busschedule.model.BusStopInfo
import com.busschedule.model.ScheduleTicket

data class ScheduleUI(
    val id: Int = 0,
    val name: String = "",
    val days: List<String> = emptyList(),
    val startTime: String = "",
    val endTime: String = "",
    val busStopInfos: List<BusStopInfo> = emptyList(),
    val desBusStopName: String = "",
    private val alarmInit: Boolean = false
) {
    private var isCheckedAlarm by mutableStateOf(alarmInit)

    fun updateAlarm() = apply { this.isCheckedAlarm = !this.isCheckedAlarm }

    fun getAlarm() = isCheckedAlarm
}

fun ScheduleTicket.asStateUI() =
    ScheduleUI(
        id = id,
        name = name,
        days = daysList,
        startTime = startTime,
        endTime = endTime,
        busStopInfos = busStopInfos,
        desBusStopName = desBusStopName,
        alarmInit = isAlarmOn
    )