package com.example.berlinclockkataandroid.ui.clock.mapper

import com.example.berlinclockkataandroid.ui.clock.BerlinColor

object UiColorMapper {
    fun map(pattern: String): List<BerlinColor> {
        return pattern.map { char ->
            when (char) {
                'R' -> BerlinColor.RED
                'Y' -> BerlinColor.YELLOW
                'O' -> BerlinColor.OFF
                else -> BerlinColor.OFF
            }
        }
    }
}