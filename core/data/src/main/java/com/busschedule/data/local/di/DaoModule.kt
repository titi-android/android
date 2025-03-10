package com.busschedule.data.local.di

import com.busschedule.data.local.room.database.RoomDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Singleton
    @Provides
    fun provideNotifyScheduleDao(db: RoomDataBase) = db.notifyScheduleDao()
}