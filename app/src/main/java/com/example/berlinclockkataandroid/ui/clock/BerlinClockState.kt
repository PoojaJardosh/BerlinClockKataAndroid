package com.example.berlinclockkataandroid.ui.clock

data class BerlinClockState(
    val secondsLamp: Lamp,
    val fiveHourRowLamps: List<Lamp>,
    val oneHourRowLamps: List<Lamp>,
    val fiveMinuteRowLamps: List<Lamp>,
    val oneMinuteRowLamps: List<Lamp>,
    val digitalTime: String = "00:00:00",
    val birlinClockString: String = ""
)