package com.example.berlinclockkataandroid.domain

class BerlinClockInputValidator {


    private val timePattern = Regex("^\\d{2}:\\d{2}:\\d{2}\$")
    fun isValid(time: String): Boolean {
        return time.matches(timePattern)
    }
}