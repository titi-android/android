package com.busschedule.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.busschedule.data.local.room.model.EntityTable
import com.busschedule.data.local.room.model.ScheduleRegisterEntity

@Dao
interface RegisterScheduleDao {
    @Insert
    fun insert(scheduleRegister: ScheduleRegisterEntity)

    @Query("SELECT * FROM ${EntityTable.REGISTER_SCHEDULE}")
    fun readAll(): List<ScheduleRegisterEntity>

    @Query("DELETE FROM ${EntityTable.REGISTER_SCHEDULE}")
    fun delete()

    @Query("SELECT EXISTS(SELECT 1 FROM ${EntityTable.REGISTER_SCHEDULE})")
    fun isExist(): Boolean

}