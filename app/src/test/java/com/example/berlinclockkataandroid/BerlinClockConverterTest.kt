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
        assertEquals("OOOO", getRow(1, "00:00:00"))
        assertEquals("OOOO", getRow(1, "04:59:59"))
    }

    @Test
    fun `convert returns correct 5-hour row for 05 to 09 hours`() {
        // 5 hours -> 5/5 = 1 lamp
        assertEquals("ROOO", getRow(1, "05:00:00"))
        assertEquals("ROOO", getRow(1, "09:59:59"))
    }

    @Test
    fun `convert returns correct 5-hour row for 10 to 14 hours`() {
        // 10 hours -> 10/5 = 2 lamps
        assertEquals("ROOO", getRow(1, "05:00:00"))
        assertEquals("ROOO", getRow(1, "09:59:59"))
    }

    @Test
    fun `convert returns correct 5-hour row for 15 to 19 hours`() {
        // 15 hours -> 15/5 = 3 lamps
        assertEquals("RROO", getRow(1, "10:00:00"))
        assertEquals("RROO", getRow(1, "14:59:59"))
    }

    @Test
    fun `convert returns correct 5-hour row for 20 to 23 hours`() {
        // 20 hours -> 20/5 = 4 lamps (Max)
        assertEquals("RRRR", getRow(1, "20:00:00"))
        assertEquals("RRRR", getRow(1, "23:59:59"))
    }

    // --- ROW 3: ONE HOUR ROW (Bottom Red Row) ---
    // Logic: Remainder of hours % 5. Max 4 lamps.

    @Test
    fun `convert returns correct 1-hour row for exact multiples of 5`() {
        // 0, 5, 10, 15, 20 hours -> Remainder 0
        assertEquals("OOOO", getRow(2, "00:00:00"))
        assertEquals("OOOO", getRow(2, "05:00:00"))
        assertEquals("OOOO", getRow(2, "10:00:00"))
        assertEquals("OOOO", getRow(2, "15:00:00"))
        assertEquals("OOOO", getRow(2, "20:00:00"))
    }

    @Test
    fun `convert returns correct 1-hour row for remainder 1`() {
        // 1, 6, 11, 16, 21 hours
        assertEquals("ROOO", getRow(2, "01:00:00"))
        assertEquals("ROOO", getRow(2, "06:00:00"))
        assertEquals("ROOO", getRow(2, "11:00:00"))
        assertEquals("ROOO", getRow(2, "16:00:00"))
        assertEquals("ROOO", getRow(2, "21:00:00"))
    }

    @Test
    fun `convert returns correct 1-hour row for remainder 2`() {
        // 2, 7, 12, 17, 22 hours
        assertEquals("RROO", getRow(2, "02:00:00"))
        assertEquals("RROO", getRow(2, "22:00:00"))
    }

    @Test
    fun `convert returns correct 1-hour row for remainder 3`() {
        // 3, 8, 13, 18, 23 hours
        assertEquals("RRRO", getRow(2, "03:00:00"))
        assertEquals("RRRO", getRow(2, "23:00:00"))
    }

    @Test
    fun `convert returns correct 1-hour row for remainder 4 (Full Row)`() {
        // 4, 9, 14, 19 hours
        assertEquals("RRRR", getRow(2, "04:00:00"))
        assertEquals("RRRR", getRow(2, "19:00:00"))
    }

    // --- ROW 3: FIVE MINUTE ROW (The Complex 11-Lamp Row) ---
    // Logic: 1 lamp per 5 mins. Max 11 lamps.
    // Color Rule: 3rd, 6th, 9th lamps are RED (R). All others YELLOW (Y).

    @Test
    fun `convert returns correct 5-minute row for 00 to 04 minutes`() {
        // 0 mins -> 0 lamps
        assertEquals("OOOOOOOOOOO", getRow(3, "00:00:00"))
        assertEquals("OOOOOOOOOOO", getRow(3, "00:04:00"))
    }

    @Test
    fun `convert returns correct 5-minute row for 05 to 14 minutes (Yellows only)`() {
        // 5 mins -> 1 lamp (Y)
        assertEquals("YOOOOOOOOOO", getRow(3, "00:05:00"))
        // 10 mins -> 2 lamps (YY)
        assertEquals("YYOOOOOOOOO", getRow(3, "00:10:00"))
    }

    // --- HELPER FUNCTION ---
    private fun getRow(index: Int, time: String): String {
        val output = converter.convert(time)
        val rows = output.split("\n")
        return if (rows.size > index) rows[index] else ""
    }
}