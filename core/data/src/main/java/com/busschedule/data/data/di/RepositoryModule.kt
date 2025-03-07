package com.busschedule.data.di

import com.busschedule.data.repository.BusRepositoryImpl
import com.busschedule.data.repository.BusStopRepositoryImpl
import com.busschedule.data.repository.FCMRepositoryImpl
import com.busschedule.data.repository.UserRepositoryImpl
import com.busschedule.data.repository.ScheduleRepositoryImpl
import com.busschedule.domain.repository.BusRepository
import com.busschedule.domain.repository.BusStopRepository
import com.busschedule.domain.repository.FCMRepository
import com.busschedule.domain.repository.UserRepository
import com.busschedule.domain.repository.ScheduleRepository
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