package com.example.berlinclockkataandroid.presentation

import com.example.berlinclockkataandroid.ui.clock.BerlinColor
import com.example.berlinclockkataandroid.ui.clock.BerlinHours
import com.example.berlinclockkataandroid.ui.clock.Lamp
import junit.framework.TestCase.assertEquals
import kotlin.test.Test

class BerlinHoursTest {
    private val berlinHours = BerlinHours()

    @Test
    fun `getFiveHourRow returns correct patterns`() {
        assertLamps(berlinHours.getFiveHourRow(0),  0) // 00:00 -> OOOO
        assertLamps(berlinHours.getFiveHourRow(5),  1) // 05:00 -> ROOO
        assertLamps(berlinHours.getFiveHourRow(10), 2) // 10:00 -> RROO
        assertLamps(berlinHours.getFiveHourRow(15), 3) // 15:00 -> RRRO
        assertLamps(berlinHours.getFiveHourRow(23), 4) // 20:00 -> RRRR
    }

    private fun assertLamps(actualLamps: List<Lamp>, expectedRedCount: Int) {
        val expectedLamps = List(4) { index ->
            if (index < expectedRedCount) Lamp(BerlinColor.RED) else Lamp(BerlinColor.OFF)
        }
        assertEquals(expectedLamps, actualLamps)
    }
}