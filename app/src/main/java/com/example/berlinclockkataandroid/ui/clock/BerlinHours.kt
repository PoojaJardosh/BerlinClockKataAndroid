package com.example.berlinclockkataandroid.ui.clock

import com.example.berlinclockkataandroid.ui.clock.BerlinClockConstants.FIVE_HOURS_LAMP_COUNT
import com.example.berlinclockkataandroid.ui.clock.BerlinClockConstants.HOURS_PER_FIVE_HOUR_LAMP
import com.example.berlinclockkataandroid.ui.clock.BerlinClockConstants.SINGLE_HOUR_LAMP_COUNT
import javax.inject.Inject

class BerlinHours @Inject constructor() {

    fun getFiveHourRow(hours: Int): List<Lamp> {
        val onLampsCount = hours / HOURS_PER_FIVE_HOUR_LAMP
        return List(FIVE_HOURS_LAMP_COUNT) { index ->
            if (index < onLampsCount) {
                Lamp(BerlinColor.RED)
            } else {
                Lamp(BerlinColor.OFF)
            }
        }
    }
    fun getSingleHourRow(hours: Int): List<Lamp> {
        val onLampsCount = hours % HOURS_PER_FIVE_HOUR_LAMP
        return List(SINGLE_HOUR_LAMP_COUNT) { index ->
            if (index < onLampsCount) {
                Lamp(BerlinColor.RED)
            } else {
                Lamp(BerlinColor.OFF)
            }
        }
    }
}