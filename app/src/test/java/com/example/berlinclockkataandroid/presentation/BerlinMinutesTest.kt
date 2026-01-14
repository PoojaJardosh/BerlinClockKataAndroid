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

    private fun assertFiveMinuteRow(lamps: List<Lamp>, onCount: Int) {
        assertEquals(11, lamps.size)
        assertEquals(onCount, lamps.count { it.color != BerlinColor.OFF })
    }
}