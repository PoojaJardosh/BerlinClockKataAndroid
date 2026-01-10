package com.example.berlinclockkataandroid.domain

class BerlinClockConverter {
    fun convert(time: String): String {
        val parts = time.split(":").map { it.toInt() }
        val h = parts[0]
        val m = parts[1]
        val s = parts[2]

        return buildString {
            append(if (s % 2 == 0) "Y" else "O").append("\n")
            append(generateRow(h / 5, 4, 'R'))
        }
    }
    private fun generateRow(litCount: Int, totalLamps: Int, onChar: Char): String {
        return onChar.toString().repeat(litCount) + "O".repeat(totalLamps - litCount)
    }

}