package com.busschedule.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.busschedule.data.local.room.model.EntityTable
import com.busschedule.data.local.room.model.NotifyScheduleEntity
import com.busschedule.model.BusStopInfo

@Dao
interface NotifyScheduleDao {
    @Insert
    fun insert(notifySchedule: NotifyScheduleEntity)

    @Query("DELETE FROM ${EntityTable.NOTIFY_SCHEDULE} WHERE scheduleId = :scheduleId")
    fun deleteById(scheduleId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM ${EntityTable.NOTIFY_SCHEDULE} WHERE scheduleId = :scheduleId)")
    fun isExist(scheduleId: String): Boolean

    @Query("SELECT * FROM ${EntityTable.NOTIFY_SCHEDULE} WHERE scheduleId = :scheduleId")
    fun read(scheduleId: String): NotifyScheduleEntity

    @Query("SELECT busStopIndex FROM ${EntityTable.NOTIFY_SCHEDULE} WHERE scheduleId = :scheduleId")
    fun readBusStopIndex(scheduleId: String): Int

    @Query("""
        UPDATE ${EntityTable.NOTIFY_SCHEDULE} 
        SET scheduleName = :scheduleName,
        busStopInfos = :busStopInfos
        WHERE scheduleId = :scheduleId
    """)
    fun update(scheduleId: String, scheduleName: String, busStopInfos: List<BusStopInfo>)

    @Query("""
        UPDATE ${EntityTable.NOTIFY_SCHEDULE} 
        SET busStopIndex = :busStopIndex
        WHERE scheduleId = :scheduleId
    """)
    fun updateBusStopIndex(scheduleId: String, busStopIndex: Int)
}