package com.example.berlinclockkataandroid.ui.clock

class BerlinSeconds {
    fun convert(seconds: Int): Lamp {
        return if (seconds % 2 == 0) {
            Lamp(BerlinColor.YELLOW)
        } else {
            Lamp(BerlinColor.OFF)
        }
    }
}