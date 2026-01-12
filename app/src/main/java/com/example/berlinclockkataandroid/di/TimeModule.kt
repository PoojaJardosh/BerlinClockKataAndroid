package com.example.berlinclockkataandroid.di

import com.example.berlinclockkataandroid.data.RealTimeProviderImpl
import com.example.berlinclockkataandroid.domain.TimeProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TimeModule {

    @Binds
    @Singleton
    abstract fun bindTimeProvider(
        realTimeProviderImpl: RealTimeProviderImpl
    ): TimeProvider
}