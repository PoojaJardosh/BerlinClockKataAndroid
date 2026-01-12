package com.example.berlinclockkataandroid.domain

data class BerlinTime(
    val hour: Int,
    val minute: Int,
    val second: Int
) {
    init {
        require(hour in 0..23) { "Hour must be between 0 and 23" }
        require(minute in 0..59) { "Minute must be between 0 and 59" }
        require(second in 0..59) { "Second must be between 0 and 59" }
    }
}