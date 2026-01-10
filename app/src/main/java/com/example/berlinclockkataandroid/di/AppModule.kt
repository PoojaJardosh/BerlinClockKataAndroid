package com.example.berlinclockkataandroid.di

import com.example.berlinclockkataandroid.domain.BerlinClockConverter
import com.example.berlinclockkataandroid.domain.BerlinClockInputValidator
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
    fun provideValidator(): BerlinClockInputValidator = BerlinClockInputValidator()

    @Provides
    @Singleton
    fun provideConverter(): BerlinClockConverter = BerlinClockConverter()

}