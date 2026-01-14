package com.example.berlinclockkataandroid.ui.clock

import javax.inject.Inject

class BerlinSeconds @Inject constructor() {
    fun convert(seconds: Int): Lamp {
        return if (seconds % 2 == 0) {
            Lamp(BerlinColor.YELLOW)
        } else {
            Lamp(BerlinColor.OFF)
        }
    }
}