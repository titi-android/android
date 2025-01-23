package com.busschedule.data.di

import com.busschedule.data.repository.BusStopRepositoryImpl
import com.busschedule.data.repository.LoginRepositoryImpl
import com.busschedule.data.repository.ScheduleRepositoryImpl
import com.busschedule.domain.repository.BusStopRepository
import com.busschedule.domain.repository.LoginRepository
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
        loginRepositoryImpl: LoginRepositoryImpl,
    ): LoginRepository

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
}