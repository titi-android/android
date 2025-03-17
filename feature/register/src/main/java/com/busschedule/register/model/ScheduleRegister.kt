package com.busschedule.register.model

import com.busschedule.model.BusStop
import com.busschedule.model.DayOfWeekUi

data class ScheduleRegister (
    val name: String = "",
    val dayOfWeeks: List<DayOfWeekUi> = emptyList(),
    val startTime: String = "",
    val endTime: String = "",
    val isNotify: Boolean = false,
    val routeInfos: List<BusStopInfoUI> = emptyList(),
    val arriveBusStop: BusStop = BusStop(),
)
