package com.example.berlinclockkataandroid.domain

import java.time.LocalTime

interface TimeProvider {
    fun getCurrentTime(): LocalTime
}