package com.example.berlinclockkataandroid.domain

class BerlinClockInputValidator {

    /**
     * Regex Breakdown:
     * ^([01]\d|2[0-3]) -> Hours: 00-19 or 20-23
     * :([0-5]\d)       -> Minutes: 00-59
     * :([0-5]\d)$      -> Seconds: 00-59
     */
    private val timePattern = Regex("^([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$")
    fun isValid(time: String): Boolean {
        return time.matches(timePattern)
    }
}