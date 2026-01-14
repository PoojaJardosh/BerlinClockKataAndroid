package com.example.berlinclockkataandroid.ui.clock

import javax.inject.Inject

class BerlinMinutes @Inject constructor(){
    fun getFiveMinuteRow(minutes: Int): List<Lamp> {
        val onLampsCount = minutes / 5
        return List(11) { index ->
            if (index < onLampsCount) {
                if ((index + 1) % 3 == 0) {
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
        val onCount = minutes % 5
        return List(4) { index ->
            if (index < onCount) Lamp(BerlinColor.YELLOW) else Lamp(BerlinColor.OFF)
        }
    }

}