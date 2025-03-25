package com.busschedule.data

import com.busschedule.data.data.di.RepositoryModule
import com.busschedule.data.data.repository.BusRepositoryImpl
import com.busschedule.data.data.repository.UserRepositoryImpl
import com.busschedule.domain.repository.BusRepository
import com.busschedule.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
abstract class FakeRepositoryModule {
    @Binds
    abstract fun bindsBusRepository(busRepositoryImpl: BusRepositoryImpl): BusRepository

    @Binds
    abstract fun bindsUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}