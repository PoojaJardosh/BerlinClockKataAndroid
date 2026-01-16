package com.example.berlinclockkataandroid.domain

import com.example.berlinclockkataandroid.ui.clock.BerlinClockState
import com.example.berlinclockkataandroid.ui.clock.BerlinHours
import com.example.berlinclockkataandroid.ui.clock.BerlinMinutes
import com.example.berlinclockkataandroid.ui.clock.BerlinSeconds
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class BerlinClockConverter @Inject constructor(
    private val berlinSeconds: BerlinSeconds,
    private val berlinHours: BerlinHours,
    private val berlinMinutes: BerlinMinutes
){

    fun calculate(time: LocalTime): BerlinClockState {
        return BerlinClockState(
            secondsLamp = berlinSeconds.convert(time.second),
            fiveHourRowLamps = berlinHours.getFiveHourRow(time.hour),
            oneHourRowLamps = berlinHours.getSingleHourRow(time.hour),
            fiveMinuteRowLamps = berlinMinutes.getFiveMinuteRow(time.minute),
            oneMinuteRowLamps = berlinMinutes.getSingleMinuteRow(time.minute),
            digitalTime =  time.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
            birlinClockString = convert(time)
        )
    }
    fun convert(time: LocalTime): String {
        val parts = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
            .split(":").map { it.toInt() }
        val hours = parts[0]
        val minutes = parts[1]
        val seconds = parts[2]

        return buildString {
            append(if (seconds % 2 == 0) "Y" else "O").append("\n")
            append(generateRow(hours / 5, 4, 'R')).append("\n")
            append(generateRow(hours % 5, 4, 'R')).append("\n")
            append(generateFiveMinuteRow(minutes / 5)).append("\n")
            append(generateRow(minutes % 5, 4, 'Y'))
        }
    }
    private fun generateRow(litCount: Int, totalLamps: Int, onChar: Char): String {
        return onChar.toString().repeat(litCount) + "O".repeat(totalLamps - litCount)
    }
    private fun generateFiveMinuteRow(litCount: Int): String {
        val totalLamps = 11
        return (1..totalLamps).joinToString("") { index ->
            if (index <= litCount) {
                if (index % 3 == 0) "R" else "Y"
            } else {
                "O"
            }
        }
    }
}