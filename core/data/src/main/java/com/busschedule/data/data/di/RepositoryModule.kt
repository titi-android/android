package com.busschedule.data.data.di

import com.busschedule.data.data.repository.BusRepositoryImpl
import com.busschedule.data.data.repository.BusStopRepositoryImpl
import com.busschedule.data.data.repository.FCMRepositoryImpl
import com.busschedule.data.data.repository.ScheduleRepositoryImpl
import com.busschedule.data.data.repository.UserRepositoryImpl
import com.busschedule.domain.repository.BusRepository
import com.busschedule.domain.repository.BusStopRepository
import com.busschedule.domain.repository.FCMRepository
import com.busschedule.domain.repository.ScheduleRepository
import com.busschedule.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLoginRepository(
        loginRepositoryImpl: UserRepositoryImpl,
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindScheduleRepository(
        scheduleRepositoryImpl: ScheduleRepositoryImpl
    ): ScheduleRepository

    @Binds
    @Singleton
    abstract fun bindBusStopRepository(
        busStopRepositoryImpl: BusStopRepositoryImpl
    ): BusStopRepository

    @Binds
    @Singleton
    abstract fun bindBusRepository(
        busRepositoryImpl: BusRepositoryImpl
    ): BusRepository

    @Binds
    @Singleton
    abstract fun bindFCMRepository(
        fcmRepositoryImpl: FCMRepositoryImpl
    ): FCMRepository
}