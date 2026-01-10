package com.example.berlinclockkataandroid

import com.example.berlinclockkataandroid.domain.BerlinClockConverter
import org.junit.Test
import org.junit.Assert.assertEquals

class BerlinClockConverterTest {

    private val converter = BerlinClockConverter()

    @Test
    fun `convert returns Y for even seconds (Midnight)`() {
        val output = converter.convert("00:00:00")
        assertEquals("Y", output.substringBefore("\n"))
    }

    @Test
    fun `convert returns O for odd seconds`() {
        val output = converter.convert("00:00:01")
        assertEquals("O", output.substringBefore("\n"))
    }

    // --- ROW 2: FIVE HOUR ROW (Top Red Row) ---
    // Logic: 1 lamp for every 5 hours. Max 4 lamps.
    @Test
    fun `convert returns correct 5-hour row for 00 to 04 hours`() {
        // 0 hours -> 0/5 = 0 lamps
        val output = converter.convert("04:00:00")
        assertEquals("OOOO", output.substringAfter("\n"))
    }

    @Test
    fun `convert returns correct 5-hour row for 10 to 14 hours`() {
        // 10 hours -> 10/5 = 2 lamps
        val output = converter.convert("10:00:00")
        assertEquals("RROO", output.substringAfter("\n"))
    }

    @Test
    fun `convert returns correct 5-hour row for 15 to 19 hours`() {
        // 15 hours -> 15/5 = 3 lamps
        val output = converter.convert("15:00:00")
        assertEquals("RRRO", output.substringAfter("\n"))
    }
}