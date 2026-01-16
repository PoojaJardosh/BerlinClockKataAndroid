package com.example.berlinclockkataandroid.ui.clock

import com.example.berlinclockkataandroid.ui.clock.BerlinClockConstants.SECONDS_BLINK_INTERVAL
import javax.inject.Inject

class BerlinSeconds @Inject constructor() {
    fun convert(seconds: Int): Lamp {
        return if (seconds % SECONDS_BLINK_INTERVAL == 0) {
            Lamp(BerlinColor.YELLOW)
        } else {
            Lamp(BerlinColor.OFF)
        }
    }
}