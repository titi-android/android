package com.busschedule.data.remote.di

import com.busschedule.data.BuildConfig
import com.busschedule.data.remote.network.ResultCallAdapterFactory
import com.busschedule.data.remote.network.auth.AuthAuthenticator
import com.busschedule.data.remote.network.auth.AuthInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val httpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BusScheduleOkhttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BusScheduleAuthOkhttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BusScheduleRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BusScheduleAuthRetrofit

    @Provides
    fun provideJson(): Json {
        return Json {
            prettyPrint = true
            coerceInputValues = true
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
    }

    @BusScheduleOkhttpClient
    @Provides
    @Singleton
    fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @BusScheduleAuthOkhttpClient
    @Provides
    @Singleton
    fun provideAuthClient(authenticator: AuthInterceptor, authAuthenticator: AuthAuthenticator): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authenticator)
            .authenticator(authAuthenticator)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @BusScheduleRetrofit
    @Provides
    @Singleton
    fun provideRetrofit(
        @BusScheduleOkhttpClient okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaTypeOrNull()!!))
            .build()
    }

    @BusScheduleAuthRetrofit
    @Provides
    @Singleton
    fun provideAuthRetrofit(
        @BusScheduleAuthOkhttpClient okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaTypeOrNull()!!))
            .build()
    }
}