package com.example.berlinclockkataandroid

import com.example.berlinclockkataandroid.ui.clock.BerlinSeconds
import com.example.berlinclockkataandroid.ui.clock.BerlinColor
import kotlin.test.Test
import kotlin.test.assertEquals

class BerlinSecondsTest {
    private val berlinSeconds = BerlinSeconds()

    @Test
    fun `convert returns YELLOW for even seconds`() {
        assertEquals(BerlinColor.YELLOW, berlinSeconds.convert(0).color)
        assertEquals(BerlinColor.YELLOW, berlinSeconds.convert(2).color)
        assertEquals(BerlinColor.YELLOW, berlinSeconds.convert(10).color)
        assertEquals(BerlinColor.YELLOW, berlinSeconds.convert(58).color)
    }

    @Test
    fun `convert returns OFF for odd seconds`() {
        assertEquals(BerlinColor.OFF, berlinSeconds.convert(1).color)
        assertEquals(BerlinColor.OFF, berlinSeconds.convert(3).color)
        assertEquals(BerlinColor.OFF, berlinSeconds.convert(17).color)
        assertEquals(BerlinColor.OFF, berlinSeconds.convert(59).color)
    }
}