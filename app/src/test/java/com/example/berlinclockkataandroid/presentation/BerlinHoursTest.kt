package com.example.berlinclockkataandroid.presentation

import com.example.berlinclockkataandroid.ui.clock.BerlinColor
import com.example.berlinclockkataandroid.ui.clock.BerlinHours
import com.example.berlinclockkataandroid.ui.clock.Lamp
import junit.framework.TestCase.assertEquals
import kotlin.test.Test

class BerlinHoursTest {
    private val berlinHours = BerlinHours()

    @Test
    fun `getFiveHourRow - 0 lamps ON (Hours 00-04)`() {
        val testCases = listOf(0, 1, 2, 3, 4)

        testCases.forEach { hour ->
            assertLamps(
                actualLamps = berlinHours.getFiveHourRow(hour),
                expectedRedCount = 0,
            )
        }
    }
    @Test
    fun `getFiveHourRow - 1 lamp ON (Hours 05-09)`() {
        val testCases = listOf(5, 6, 7, 8, 9)

        testCases.forEach { hour ->
            assertLamps(
                actualLamps = berlinHours.getFiveHourRow(hour),
                expectedRedCount = 1,
            )
        }
    }

    private fun assertLamps(actualLamps: List<Lamp>, expectedRedCount: Int) {
        val expectedLamps = List(4) { index ->
            if (index < expectedRedCount) Lamp(BerlinColor.RED) else Lamp(BerlinColor.OFF)
        }
        assertEquals(expectedLamps, actualLamps)
    }
}