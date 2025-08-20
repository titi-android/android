package com.busschedule.data.data.di

import com.busschedule.data.data.repository.BusRepositoryImpl
import com.busschedule.data.data.repository.BusStopRepositoryImpl
import com.busschedule.data.data.repository.FCMRepositoryImpl
import com.busschedule.data.data.repository.NotifyRepositoryImpl
import com.busschedule.data.data.repository.RecentlySearchBusStopRepositoryImpl
import com.busschedule.data.data.repository.ScheduleRepositoryImpl
import com.busschedule.data.data.repository.SubwayRepositoryImpl
import com.busschedule.data.data.repository.TempSaveScheduleRepositoryImpl
import com.busschedule.data.data.repository.TokenRepositoryImpl
import com.busschedule.data.data.repository.UserRepositoryImpl
import com.busschedule.domain.repository.BusRepository
import com.busschedule.domain.repository.BusStopRepository
import com.busschedule.domain.repository.FCMRepository
import com.busschedule.domain.repository.NotifyRepository
import com.busschedule.domain.repository.RecentlySearchBusStopRepository
import com.busschedule.domain.repository.ScheduleRepository
import com.busschedule.domain.repository.SubwayRepository
import com.busschedule.domain.repository.TempSaveScheduleRepository
import com.busschedule.domain.repository.TokenRepository
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

    @Binds
    @Singleton
    abstract fun bindTokenRepository(
        tokenRepositoryImpl: TokenRepositoryImpl
    ): TokenRepository

    @Binds
    @Singleton
    abstract fun bindNotifyRepository(
        notifyRepositoryImpl: NotifyRepositoryImpl
    ): NotifyRepository

    @Binds
    @Singleton
    abstract fun bindRecentlySearchBusStopRepository(
        recentlySearchBusStopRepositoryImpl: RecentlySearchBusStopRepositoryImpl
    ): RecentlySearchBusStopRepository

    @Binds
    @Singleton
    abstract fun bindTempSaveScheduledRepository(
        tempSaveScheduleRepositoryImpl: TempSaveScheduleRepositoryImpl
    ): TempSaveScheduleRepository

    @Binds
    @Singleton
    abstract fun bindSubwayRepository(
        subwayRepositoryImpl: SubwayRepositoryImpl
    ): SubwayRepository
}