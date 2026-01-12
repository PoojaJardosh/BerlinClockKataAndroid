package com.example.berlinclockkataandroid.presentation

data class BerlinClockState(
    val secondsLamp: Boolean = false,
    val fiveHourRow: String = "OOOO",
    val oneHourRow: String = "OOOO",
    val fiveMinuteRow: String = "OOOOOOOOOOO",
    val oneMinuteRow: String = "OOOO",
    val digitalTime: String = "00:00:00"
)