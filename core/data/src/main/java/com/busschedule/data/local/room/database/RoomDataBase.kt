package com.busschedule.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.busschedule.data.local.room.Converters
import com.busschedule.data.local.room.dao.NotifyScheduleDao
import com.busschedule.data.local.room.dao.RecentlySearchBusStopDao
import com.busschedule.data.local.room.dao.RegisterScheduleDao
import com.busschedule.data.local.room.model.RecentlySearchBusStopEntity
import com.busschedule.data.local.room.model.ScheduleNotifyEntity
import com.busschedule.data.local.room.model.ScheduleRegisterEntity

@Database(
    entities = [
        ScheduleNotifyEntity::class,
        RecentlySearchBusStopEntity::class,
        ScheduleRegisterEntity::class
               ],
    version = 5
)
@TypeConverters(Converters::class)
abstract class RoomDataBase : RoomDatabase() {
    abstract fun notifyScheduleDao(): NotifyScheduleDao
    abstract fun recentlySearchBusStopDao(): RecentlySearchBusStopDao
    abstract fun registerScheduleDao(): RegisterScheduleDao
}