package com.example.berlinclockkataandroid.di

import com.example.berlinclockkataandroid.domain.BerlinClockConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideConverter(): BerlinClockConverter {
        return BerlinClockConverter()
    }

}
