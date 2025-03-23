package com.busschedule.model

data class ScheduleNotify(
    val scheduleId: String,
    val scheduleName: String,
    val busStopIndex: Int = 0,
    val busStopInfos: List<BusStopInfo> = emptyList()
)
