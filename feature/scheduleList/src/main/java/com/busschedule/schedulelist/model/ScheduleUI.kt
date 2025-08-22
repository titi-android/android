package com.busschedule.schedulelist.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.busschedule.model.Schedule
import com.busschedule.model.TransitInfo
import com.busschedule.model.constant.TransitConst

data class ScheduleUI(
    val id: Int = 0,
    val name: String = "",
    val days: List<String> = emptyList(),
    val startTime: List<Int> = emptyList(),
    val endTime: List<Int> = emptyList(),
    val sections: List<TransitInfo> = emptyList(),
    val destinationType: String = TransitConst.BUS.name,
    val destinationName: String = "",
    private val alarmInit: Boolean = false
) {
    private var isCheckedAlarm by mutableStateOf(alarmInit)

    fun updateAlarm() = apply { this.isCheckedAlarm = !this.isCheckedAlarm }

    fun getAlarm() = isCheckedAlarm
}

fun Schedule.asStateUI() =
    ScheduleUI(
        id = id,
        name = name,
        days = daysList,
        startTime = startTime,
        endTime = endTime,
        sections = sections,
        destinationType = destinationType,
        destinationName = destinationName,
        alarmInit = isAlarmOn
    )