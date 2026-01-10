package com.example.berlinclockkataandroid.domain

class BerlinClockConverter {
    fun convert(time: String): String {
        val parts = time.split(":")
        val seconds = parts[2].toInt()
        return if (seconds % 2 == 0) "Y" else "O"
    }
}