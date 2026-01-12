package com.example.berlinclockkataandroid.data

import com.example.berlinclockkataandroid.domain.TimeProvider
import java.time.LocalTime
import javax.inject.Inject

class RealTimeProviderImpl @Inject constructor() : TimeProvider {
    override fun getCurrentTime(): LocalTime {
        return LocalTime.now()
    }
}