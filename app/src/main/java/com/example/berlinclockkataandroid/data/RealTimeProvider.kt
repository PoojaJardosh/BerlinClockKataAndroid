package com.example.berlinclockkataandroid.data

import com.example.berlinclockkataandroid.domain.TimeProvider
import java.time.LocalTime

class RealTimeProvider : TimeProvider {
    override fun getCurrentTime(): LocalTime {
        return LocalTime.now()
    }
}