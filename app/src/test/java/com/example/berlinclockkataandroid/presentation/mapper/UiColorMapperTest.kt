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
}