package com.example.berlinclockkataandroid.presentation.mapper

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