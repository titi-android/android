package com.busschedule.model

data class ScheduleNotify(
    val scheduleId: String,
    val scheduleName: String,
    val notifyIndex: Int = 0,
    val notifyMessages: List<NotifyMessage> = emptyList()
)
