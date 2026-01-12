package com.example.berlinclockkataandroid.presentation.mapper

import junit.framework.TestCase.assertEquals
import org.junit.Test

class UiColorMapperTest {
    // We test the logic that maps 'R' -> Red, 'Y' -> Yellow, 'O' -> DarkGray

    @Test
    fun `should map 'R' character to RED color`() {
        val input = "RRRR"
        val expected = listOf(
            BerlinColor.RED,
            BerlinColor.RED,
            BerlinColor.RED,
            BerlinColor.RED
        )
        assertEquals(expected, UiColorMapper.map(input))
    }

    @Test
    fun `should map 'Y' character to YELLOW color`() {
        val input = "YYYY"
        val expected = listOf(
            BerlinColor.YELLOW,
            BerlinColor.YELLOW,
            BerlinColor.YELLOW,
            BerlinColor.YELLOW
        )

        assertEquals(expected, UiColorMapper.map(input))
    }

    @Test
    fun `should map 'O' character to OFF color`() {
        val input = "OOOO"
        val expected = listOf(
            BerlinColor.OFF,
            BerlinColor.OFF,
            BerlinColor.OFF,
            BerlinColor.OFF
        )

        assertEquals(expected, UiColorMapper.map(input))
    }

    @Test
    fun `should map mixed valid string (R, Y, O) correctly`() {
        // 5-minute row pattern: Yellow, Yellow, Red, Off
        val input = "YYRO"
        val expected = listOf(
            BerlinColor.YELLOW,
            BerlinColor.YELLOW,
            BerlinColor.RED,
            BerlinColor.OFF
        )

        assertEquals(expected, UiColorMapper.map(input))
    }

}