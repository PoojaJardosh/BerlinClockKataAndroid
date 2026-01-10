package com.example.berlinclockkataandroid.domain

class BerlinClockConverter {
    fun convert(time: String): String {
        val parts = time.split(":").map { it.toInt() }
        val h = parts[0]
        val m = parts[1]
        val s = parts[2]

        return buildString {
            append(if (s % 2 == 0) "Y" else "O").append("\n")
            append(generateRow(h / 5, 4, 'R')).append("\n")
            append(generateRow(h % 5, 4, 'R')).append("\n")
            append(generateFiveMinuteRow(m / 5)).append("\n")
            append(generateRow(m % 5, 4, 'Y'))
        }
    }
    private fun generateRow(litCount: Int, totalLamps: Int, onChar: Char): String {
        return onChar.toString().repeat(litCount) + "O".repeat(totalLamps - litCount)
    }
    private fun generateFiveMinuteRow(litCount: Int): String {
        val totalLamps = 11
        return (1..totalLamps).joinToString("") { index ->
            if (index <= litCount) {
                // Every 3rd lamp (3, 6, 9) is Red, others Yellow
                if (index % 3 == 0) "R" else "Y"
            } else {
                "O"
            }
        }
    }

}