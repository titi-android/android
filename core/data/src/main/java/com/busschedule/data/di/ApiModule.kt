package com.busschedule.data.di

import com.busschedule.data.api.BusApi
import com.busschedule.data.api.BusStopApi
import com.busschedule.data.api.FCMApi
import com.busschedule.data.api.LoginApi
import com.busschedule.data.api.ScheduleApi
import com.busschedule.data.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideLoginApi(@NetworkModule.BusScheduleRetrofit retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }
    @Provides
    fun provideUserApi(@NetworkModule.BusScheduleRetrofit retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
    @Provides
    fun provideScheduleApi(@NetworkModule.BusScheduleAuthRetrofit retrofit: Retrofit): ScheduleApi {
        return retrofit.create(ScheduleApi::class.java)
    }

    @Provides
    fun provideBusStopApi(@NetworkModule.BusScheduleAuthRetrofit retrofit: Retrofit): BusStopApi {
        return retrofit.create(BusStopApi::class.java)
    }

    @Provides
    fun provideBusApi(@NetworkModule.BusScheduleAuthRetrofit retrofit: Retrofit): BusApi {
        return retrofit.create(BusApi::class.java)
    }

    @Provides
    fun provideFCMApi(@NetworkModule.BusScheduleAuthRetrofit retrofit: Retrofit): FCMApi {
        return retrofit.create(FCMApi::class.java)
    }
}