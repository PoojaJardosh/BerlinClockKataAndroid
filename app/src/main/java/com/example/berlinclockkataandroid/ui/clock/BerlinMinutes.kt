package com.example.berlinclockkataandroid.ui.clock

import com.example.berlinclockkataandroid.ui.clock.BerlinClockConstants.FIVE_MINUTES_LAMP_COUNT
import com.example.berlinclockkataandroid.ui.clock.BerlinClockConstants.MINUTES_PER_FIVE_MINUTE_LAMP
import com.example.berlinclockkataandroid.ui.clock.BerlinClockConstants.QUARTER_INDICATOR_INTERVAL
import com.example.berlinclockkataandroid.ui.clock.BerlinClockConstants.SINGLE_MINUTE_LAMP_COUNT
import javax.inject.Inject

class BerlinMinutes @Inject constructor(){
    fun getFiveMinuteRow(minutes: Int): List<Lamp> {
        val onLampsCount = minutes / MINUTES_PER_FIVE_MINUTE_LAMP
        return List(FIVE_MINUTES_LAMP_COUNT) { index ->
            if (index < onLampsCount) {
                if ((index + 1) % QUARTER_INDICATOR_INTERVAL == 0) {
                    Lamp(BerlinColor.RED)   // 15, 30, 45 minute marks
                } else {
                    Lamp(BerlinColor.YELLOW)
                }
            } else {
                Lamp(BerlinColor.OFF)
            }
        }
    }

    fun getSingleMinuteRow(minutes: Int): List<Lamp> {
        val onCount = minutes % MINUTES_PER_FIVE_MINUTE_LAMP
        return List(SINGLE_MINUTE_LAMP_COUNT) { index ->
            if (index < onCount) Lamp(BerlinColor.YELLOW) else Lamp(BerlinColor.OFF)
        }
    }

}