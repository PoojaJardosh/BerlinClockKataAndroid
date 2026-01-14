package com.example.berlinclockkataandroid.ui.clock

import com.example.berlinclockkataandroid.ui.mapper.BerlinColor
import com.example.berlinclockkataandroid.ui.mapper.Lamp

class BerlinSeconds {
    fun convert(seconds: Int): Lamp {
        return if (seconds % 2 == 0) {
            Lamp(BerlinColor.YELLOW)
        } else {
            Lamp(BerlinColor.OFF)
        }
    }
}