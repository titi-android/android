package com.busschedule.domain.repository

import com.busschedule.model.BusStopInfo
import com.busschedule.model.NotifySchedule

interface NotifyRepository {
    fun insert(
        scheduleId: String,
        scheduleName: String,
        busStopIndex: Int = 0,
        busStopInfos: List<BusStopInfo> = emptyList(),
    )

    fun read(scheduleId: String): NotifySchedule

    fun readBusStopIndex(scheduleId: String): Int

    fun isExist(scheduleId: String): Boolean

    fun update(scheduleId: String, scheduleName: String, busStopInfos: List<BusStopInfo>)

    fun updateBusStopIndex(scheduleId: String, busStopIndex: Int)

    suspend fun saveFCMToken(token: String)
}