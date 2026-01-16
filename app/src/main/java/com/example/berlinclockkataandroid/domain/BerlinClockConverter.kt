package com.example.berlinclockkataandroid.domain

import com.example.berlinclockkataandroid.ui.clock.BerlinClockConstants.DIGITAL_TIME_PATTERN
import com.example.berlinclockkataandroid.ui.clock.BerlinClockConstants.FIVE_HOURS_LAMP_COUNT
import com.example.berlinclockkataandroid.ui.clock.BerlinClockConstants.FIVE_MINUTES_LAMP_COUNT
import com.example.berlinclockkataandroid.ui.clock.BerlinClockConstants.HOURS_PER_FIVE_HOUR_LAMP
import com.example.berlinclockkataandroid.ui.clock.BerlinClockConstants.MINUTES_PER_FIVE_MINUTE_LAMP
import com.example.berlinclockkataandroid.ui.clock.BerlinClockConstants.QUARTER_INDICATOR_INTERVAL
import com.example.berlinclockkataandroid.ui.clock.BerlinClockConstants.SECONDS_BLINK_INTERVAL
import com.example.berlinclockkataandroid.ui.clock.BerlinClockConstants.SINGLE_HOUR_LAMP_COUNT
import com.example.berlinclockkataandroid.ui.clock.BerlinClockConstants.SINGLE_MINUTE_LAMP_COUNT
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
            digitalTime =  time.format(DateTimeFormatter.ofPattern(DIGITAL_TIME_PATTERN)),
            birlinClockString = convert(time)
        )
    }
    fun convert(time: LocalTime): String {
        val hours = time.hour
        val minutes = time.minute
        val seconds = time.second

        return buildString {
            append(if (seconds % SECONDS_BLINK_INTERVAL == 0) "Y" else "O").append("\n")
            append(generateRow(hours / HOURS_PER_FIVE_HOUR_LAMP, FIVE_HOURS_LAMP_COUNT, 'R')).append("\n")
            append(generateRow(hours % HOURS_PER_FIVE_HOUR_LAMP, SINGLE_HOUR_LAMP_COUNT, 'R')).append("\n")
            append(generateFiveMinuteRow(minutes / MINUTES_PER_FIVE_MINUTE_LAMP)).append("\n")
            append(generateRow(minutes % MINUTES_PER_FIVE_MINUTE_LAMP, SINGLE_MINUTE_LAMP_COUNT, 'Y'))
        }
    }
    private fun generateRow(litCount: Int, totalLamps: Int, onChar: Char): String {
        return onChar.toString().repeat(litCount) + "O".repeat(totalLamps - litCount)
    }
    private fun generateFiveMinuteRow(litCount: Int): String {
        return (1..FIVE_MINUTES_LAMP_COUNT).joinToString("") { index ->
            if (index <= litCount) {
                if (index % QUARTER_INDICATOR_INTERVAL == 0) "R" else "Y"
            } else {
                "O"
            }
        }
    }
}