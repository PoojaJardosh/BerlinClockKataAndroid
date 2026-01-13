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

    @Test
    fun `convert returns correct 5-hour row for 00 to 04 hours`() {
        assertEquals("OOOO", getRow(1, "00:00:00"))
        assertEquals("OOOO", getRow(1, "04:59:59"))
    }

    @Test
    fun `convert returns correct 5-hour row for 05 to 09 hours`() {
        assertEquals("ROOO", getRow(1, "05:00:00"))
        assertEquals("ROOO", getRow(1, "09:59:59"))
    }

    @Test
    fun `convert returns correct 5-hour row for 10 to 14 hours`() {
        assertEquals("RROO", getRow(1, "10:00:00"))
        assertEquals("RROO", getRow(1, "13:59:59"))
    }

    @Test
    fun `convert returns correct 5-hour row for 15 to 19 hours`() {
        assertEquals("RROO", getRow(1, "10:00:00"))
        assertEquals("RROO", getRow(1, "14:59:59"))
    }

    @Test
    fun `convert returns correct 5-hour row for 20 to 23 hours`() {
        assertEquals("RRRR", getRow(1, "20:00:00"))
        assertEquals("RRRR", getRow(1, "23:59:59"))
    }

    @Test
    fun `convert returns correct 1-hour row for exact multiples of 5`() {
        assertEquals("OOOO", getRow(2, "00:00:00"))
        assertEquals("OOOO", getRow(2, "05:00:00"))
        assertEquals("OOOO", getRow(2, "10:00:00"))
        assertEquals("OOOO", getRow(2, "15:00:00"))
        assertEquals("OOOO", getRow(2, "20:00:00"))
    }

    @Test
    fun `convert returns correct 1-hour row for remainder 1`() {
        assertEquals("ROOO", getRow(2, "01:00:00"))
        assertEquals("ROOO", getRow(2, "06:00:00"))
        assertEquals("ROOO", getRow(2, "11:00:00"))
        assertEquals("ROOO", getRow(2, "16:00:00"))
        assertEquals("ROOO", getRow(2, "21:00:00"))
    }

    @Test
    fun `convert returns correct 1-hour row for remainder 2`() {
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
        assertEquals("RRRR", getRow(2, "04:00:00"))
        assertEquals("RRRR", getRow(2, "19:00:00"))
    }

    @Test
    fun `convert returns correct 5-minute row for 00 to 04 minutes`() {
        assertEquals("OOOOOOOOOOO", getRow(3, "00:00:00"))
        assertEquals("OOOOOOOOOOO", getRow(3, "00:04:00"))
    }

    @Test
    fun `convert returns correct 5-minute row for 05 to 14 minutes (Yellows only)`() {
        assertEquals("YOOOOOOOOOO", getRow(3, "00:05:00"))
        assertEquals("YYOOOOOOOOO", getRow(3, "00:10:00"))
    }

    @Test
    fun `convert returns correct 5-minute row for 16 to 29 minutes`() {
        assertEquals("YYRYOOOOOOO", getRow(3, "00:20:00"))
        assertEquals("YYRYYOOOOOO", getRow(3, "00:25:00"))
    }

    @Test
    fun `convert returns correct 5-minute row for 30 minute half (Second Red)`() {
        assertEquals("YYRYYROOOOO", getRow(3, "00:30:00"))
    }

    @Test
    fun `convert returns correct 5-minute row for 35 to 44 minutes`() {
        assertEquals("YYRYYRYOOOO", getRow(3, "00:35:00"))
        assertEquals("YYRYYRYYOOO", getRow(3, "00:40:00"))
    }

    @Test
    fun `convert returns correct 5-minute row for 45 minute quarter (Third Red)`() {
        assertEquals("YYRYYRYYROO", getRow(3, "00:45:00"))
    }

    @Test
    fun `convert returns correct 5-minute row for 50 to 59 minutes (Full or almost full)`() {
        assertEquals("YYRYYRYYRYO", getRow(3, "00:50:00"))
        assertEquals("YYRYYRYYRYY", getRow(3, "00:55:00"))
    }

    @Test
    fun `convert returns correct 1-minute row for exact multiples of 5`() {
        assertEquals("OOOO", getRow(4, "00:00:00"))
        assertEquals("OOOO", getRow(4, "00:15:00"))
        assertEquals("OOOO", getRow(4, "00:30:00"))
    }

    @Test
    fun `convert returns correct 1-minute row for remainder 1`() {
        assertEquals("YOOO", getRow(4, "00:01:00"))
        assertEquals("YOOO", getRow(4, "00:06:00"))
    }

    @Test
    fun `convert returns correct 1-minute row for remainder 2`() {
        assertEquals("YYOO", getRow(4, "00:02:00"))
        assertEquals("YYOO", getRow(4, "00:07:00"))
    }

    @Test
    fun `convert returns correct 1-minute row for remainder 3`() {
        assertEquals("YYYO", getRow(4, "00:03:00"))
        assertEquals("YYYO", getRow(4, "00:08:00"))
    }

    @Test
    fun `convert returns correct 1-minute row for remainder 4`() {
        assertEquals("YYYY", getRow(4, "00:04:00"))
        assertEquals("YYYY", getRow(4, "00:59:00"))
    }
    private fun getRow(index: Int, time: String): String {
        val output = converter.convert(time)
        val rows = output.split("\n")
        return if (rows.size > index) rows[index] else ""
    }
}