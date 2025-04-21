package com.busschedule.data.data.repository

import com.busschedule.data.local.datastore.TokenManager
import com.busschedule.data.local.room.dao.NotifyScheduleDao
import com.busschedule.data.local.room.model.ScheduleNotifyEntity
import com.busschedule.data.local.room.model.toModel
import com.busschedule.domain.repository.NotifyRepository
import com.busschedule.model.BusStopInfo
import com.busschedule.model.ScheduleNotify
import javax.inject.Inject

class NotifyRepositoryImpl @Inject constructor(
    private val dao: NotifyScheduleDao,
    private val tokenManager: TokenManager): NotifyRepository {
    override fun insert(
        scheduleId: String,
        scheduleName: String,
        busStopIndex: Int,
        busStopInfos: List<BusStopInfo>,
    ) {
        val scheduleNotifyEntity = ScheduleNotifyEntity(
            scheduleId = scheduleId,
            scheduleName = scheduleName,
            busStopIndex = busStopIndex,
            busStopInfos = busStopInfos
        )
        dao.insert(scheduleNotifyEntity)
    }

    override fun read(scheduleId: String): ScheduleNotify =
        dao.read(scheduleId).toModel()

    override fun readBusStopIndex(scheduleId: String): Int = dao.readBusStopIndex(scheduleId)

    override fun isExist(scheduleId: String): Boolean = dao.isExist(scheduleId)


    override fun update(scheduleId: String, scheduleName: String, busStopInfos: List<BusStopInfo>) {
        dao.update(scheduleId, scheduleName, busStopInfos)
    }

    override fun updateBusStopIndex(scheduleId: String, busStopIndex: Int) {
        dao.updateBusStopIndex(scheduleId, busStopIndex)
    }

    override suspend fun saveFCMToken(token: String) {
        tokenManager.saveFCMToken(token)
    }
}