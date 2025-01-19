package com.busschedule.data.di

import com.busschedule.data.network.LoginApi
import com.busschedule.data.network.ScheduleApi
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
    fun provideScheduleApi(@NetworkModule.BusScheduleAuthRetrofit retrofit: Retrofit): ScheduleApi {
        return retrofit.create(ScheduleApi::class.java)
    }
}