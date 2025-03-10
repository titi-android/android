package com.busschedule.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.busschedule.data.local.room.model.EntityTable
import com.busschedule.data.local.room.model.NotifyScheduleEntity

@Dao
interface NotifyScheduleDao {
    @Insert
    fun insert(notifySchedule: NotifyScheduleEntity)

    @Query("DELETE FROM ${EntityTable.NOTIFY_SCHEDULE} WHERE scheduleId = :scheduleId")
    fun deleteById(scheduleId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM ${EntityTable.NOTIFY_SCHEDULE} WHERE scheduleId = :scheduleId)")
    fun isExist(scheduleId: String): Boolean
}