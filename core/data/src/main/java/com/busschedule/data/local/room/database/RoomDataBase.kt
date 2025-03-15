package com.busschedule.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.busschedule.data.local.room.Converters
import com.busschedule.data.local.room.dao.NotifyScheduleDao
import com.busschedule.data.local.room.dao.RecentlySearchBusStopDao
import com.busschedule.data.local.room.model.NotifyScheduleEntity
import com.busschedule.data.local.room.model.RecentlySearchBusStopEntity

@Database(
    entities = [NotifyScheduleEntity::class, RecentlySearchBusStopEntity::class],
    version = 4
)
@TypeConverters(Converters::class)
abstract class RoomDataBase: RoomDatabase() {
    abstract fun notifyScheduleDao(): NotifyScheduleDao
    abstract fun recentlySearchBusStopDao(): RecentlySearchBusStopDao
}