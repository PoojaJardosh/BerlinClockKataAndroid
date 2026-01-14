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
    @Test
    fun `getFiveHourRow - 2 lamps ON (Hours 10-14)`() {
        val testCases = listOf(10, 11, 12, 13, 14)

        testCases.forEach { hour ->
            assertLamps(
                actualLamps = berlinHours.getFiveHourRow(hour),
                expectedRedCount = 2,
            )
        }
    }

    @Test
    fun `getFiveHourRow - 4 lamps ON (Hours 20-23)`() {
        val testCases = listOf(20, 21, 22, 23)

        testCases.forEach { hour ->
            assertLamps(
                actualLamps = berlinHours.getFiveHourRow(hour),
                expectedRedCount = 4,
            )
        }
    }

    @Test
    fun `getSingleHourRow - 0 lamps ON (Multiples of 5)`() {
        val testCases = listOf(0, 5, 10, 15, 20)

        testCases.forEach { hour ->
            assertLamps(
                actualLamps = berlinHours.getSingleHourRow(hour),
                expectedRedCount = 0,
            )
        }
    }

    @Test
    fun `getSingleHourRow - 1 lamp ON (Remainder 1)`() {
        val testCases = listOf(1, 6, 11, 16, 21)

        testCases.forEach { hour ->
            assertLamps(
                actualLamps = berlinHours.getSingleHourRow(hour),
                expectedRedCount = 1,
            )
        }
    }

    @Test
    fun `getSingleHourRow - 2 lamps ON (Remainder 2)`() {
        val testCases = listOf(2, 7, 12, 17, 22)

        testCases.forEach { hour ->
            assertLamps(
                actualLamps = berlinHours.getSingleHourRow(hour),
                expectedRedCount = 2,
            )
        }
    }

    @Test
    fun `getSingleHourRow - 3 lamps ON (Remainder 3)`() {
        val testCases = listOf(3, 8, 13, 18, 23)

        testCases.forEach { hour ->
            assertLamps(
                actualLamps = berlinHours.getSingleHourRow(hour),
                expectedRedCount = 3,
            )
        }
    }

    @Test
    fun `getSingleHourRow - 4 lamps ON (Remainder 4)`() {
        val testCases = listOf(4, 9, 14, 19)

        testCases.forEach { hour ->
            assertLamps(
                actualLamps = berlinHours.getSingleHourRow(hour),
                expectedRedCount = 4,
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