package com.example.berlinclockkataandroid.ui.clock

import javax.inject.Inject

class BerlinHours @Inject constructor() {

    fun getFiveHourRow(hours: Int): List<Lamp> {
        val onLampsCount = hours / 5
        return List(4) { index ->
            if (index < onLampsCount) {
                Lamp(BerlinColor.RED)
            } else {
                Lamp(BerlinColor.OFF)
            }
        }
    }
}