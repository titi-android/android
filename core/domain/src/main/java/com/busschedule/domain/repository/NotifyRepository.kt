package com.busschedule.domain.repository

import com.busschedule.model.BusStopInfo
import com.busschedule.model.ScheduleNotify

interface NotifyRepository {
    suspend fun insert(
        scheduleId: String,
        scheduleName: String,
        busStopIndex: Int = 0,
        busStopInfos: List<BusStopInfo> = emptyList(),
    )

    suspend fun read(scheduleId: String): ScheduleNotify

    suspend fun readBusStopIndex(scheduleId: String): Int

    suspend fun isExist(scheduleId: String): Boolean

    suspend fun update(scheduleId: String, scheduleName: String, busStopInfos: List<BusStopInfo>)

    suspend fun updateBusStopIndex(scheduleId: String, busStopIndex: Int)

    suspend fun saveFCMToken(token: String)
}