package com.example.berlinclockkataandroid.domain

class BerlinClockInputValidator {


    private val timePattern = Regex("^([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$")
    fun isValid(time: String): Boolean {
        return time.matches(timePattern)
    }
}