package com.example.berlinclockkataandroid.domain

class BerlinClockInputValidator {
    fun isValid(time: String): Boolean {
        return time.isEmpty().not()
    }
}