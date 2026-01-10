package com.example.berlinclockkataandroid.domain

class BerlinClockInputValidator {


    private val timePattern = Regex("^([01]\\d|2[0-3]):\\d{2}:\\d{2}\$")
    fun isValid(time: String): Boolean {
        return time.matches(timePattern)
    }
}