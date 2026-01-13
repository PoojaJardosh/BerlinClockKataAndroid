package com.example.berlinclockkataandroid.ui.clock

data class BerlinClockState(
    val secondsLamp: String = "O",
    val fiveHourRow: String = "OOOO",
    val oneHourRow: String = "OOOO",
    val fiveMinuteRow: String = "OOOOOOOOOOO",
    val oneMinuteRow: String = "OOOO",
    val digitalTime: String = "00:00:00",
    val birlinClockString: String = ""
)