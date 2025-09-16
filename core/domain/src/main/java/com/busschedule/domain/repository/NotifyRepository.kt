package com.busschedule.domain.repository

import com.busschedule.model.NotifyMessage
import com.busschedule.model.ScheduleNotify

interface NotifyRepository {
    suspend fun insert(
        scheduleId: String,
        scheduleName: String,
        busStopIndex: Int = 0,
        notifyMessages: List<NotifyMessage> = emptyList(),
    )

    suspend fun read(scheduleId: String): ScheduleNotify

    suspend fun readBusStopIndex(scheduleId: String): Int

    suspend fun isExist(scheduleId: String): Boolean

    suspend fun update(scheduleId: String, scheduleName: String, notifyMessages: List<NotifyMessage>)

    suspend fun updateBusStopIndex(scheduleId: String, busStopIndex: Int)

    suspend fun saveFCMToken(token: String)
}