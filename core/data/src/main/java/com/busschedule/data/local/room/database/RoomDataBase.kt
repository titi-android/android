package com.busschedule.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.busschedule.data.local.room.Converters
import com.busschedule.data.local.room.dao.NotifyScheduleDao
import com.busschedule.data.local.room.model.NotifyScheduleEntity

@Database(
    entities = [NotifyScheduleEntity::class],
    version = 3
)
@TypeConverters(Converters::class)
abstract class RoomDataBase: RoomDatabase() {
    abstract fun notifyScheduleDao(): NotifyScheduleDao
}