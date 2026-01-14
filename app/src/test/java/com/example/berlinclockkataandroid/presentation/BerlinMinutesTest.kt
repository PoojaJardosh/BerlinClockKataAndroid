package com.example.berlinclockkataandroid.presentation

import com.example.berlinclockkataandroid.ui.clock.BerlinColor
import com.example.berlinclockkataandroid.ui.clock.BerlinMinutes
import com.example.berlinclockkataandroid.ui.clock.Lamp
import junit.framework.TestCase.assertEquals
import kotlin.test.Test

class BerlinMinutesTest {

    private val berlinMinutes = BerlinMinutes()
    @Test
    fun `getFiveMinuteRow returns all OFF for 0 to 4 minutes`() {
        assertFiveMinuteRow(berlinMinutes.getFiveMinuteRow(0), 0)
        assertFiveMinuteRow(berlinMinutes.getFiveMinuteRow(4), 0)
    }

    @Test
    fun `getFiveMinuteRow handles Standard Yellows (5, 10 mins)`() {
        val lamps5 = berlinMinutes.getFiveMinuteRow(5)
        assertEquals(BerlinColor.YELLOW, lamps5[0].color)
        assertEquals(BerlinColor.OFF, lamps5[1].color)

        val lamps10 = berlinMinutes.getFiveMinuteRow(10)
        assertEquals(BerlinColor.YELLOW, lamps10[0].color)
        assertEquals(BerlinColor.YELLOW, lamps10[1].color)
        assertEquals(BerlinColor.OFF, lamps10[2].color)
    }

    private fun assertFiveMinuteRow(lamps: List<Lamp>, onCount: Int) {
        assertEquals(11, lamps.size)
        assertEquals(onCount, lamps.count { it.color != BerlinColor.OFF })
    }
}