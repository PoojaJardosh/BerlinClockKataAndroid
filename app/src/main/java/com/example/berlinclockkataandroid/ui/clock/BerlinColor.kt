package com.example.berlinclockkataandroid.ui.mapper

/**
*Lamp colors.
*/
enum class BerlinColor {
    RED,
    YELLOW,
    OFF
}

data class Lamp(val color: BerlinColor)