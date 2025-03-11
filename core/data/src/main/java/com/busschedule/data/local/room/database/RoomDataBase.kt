package com.busschedule.data.local.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.busschedule.data.local.room.Converters
import com.busschedule.data.local.room.dao.NotifyScheduleDao
import com.busschedule.data.local.room.model.NotifyScheduleEntity

@Database(
    entities = [NotifyScheduleEntity::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class RoomDataBase: RoomDatabase() {
    abstract fun notifyScheduleDao(): NotifyScheduleDao

    companion object {
        private var instance: RoomDataBase? = null

        @Synchronized
        fun getInstance(context: Context): RoomDataBase? {
            if (instance == null) {
                synchronized(RoomDataBase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RoomDataBase::class.java,
                        "notify-database"
                    ).build()
                }
            }
            return instance
        }
    }
}