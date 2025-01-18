package com.busschedule.register.entity

data class ScheduleRegister(
    val name: String = "",
    val date: String = "",
    val time: ScheduleRangeTime = ScheduleRangeTime(),
    val city: String = "",
    val busStop: String = "",
    val bus: String = ""
)


data class ScheduleRangeTime(
    val start: String = "00:00",
    val end: String = "00:00"
)