package com.example.berlinclockkataandroid

import com.example.berlinclockkataandroid.domain.BerlinClockConverter
import com.example.berlinclockkataandroid.ui.clock.BerlinColor
import com.example.berlinclockkataandroid.ui.clock.BerlinHours
import com.example.berlinclockkataandroid.ui.clock.BerlinMinutes
import com.example.berlinclockkataandroid.ui.clock.BerlinSeconds
import com.example.berlinclockkataandroid.ui.clock.Lamp
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Before
import java.time.LocalTime

class BerlinClockConverterTest {

    private val berlinSeconds: BerlinSeconds = mockk()
    private val berlinHours: BerlinHours = mockk()
    private val berlinMinutes: BerlinMinutes = mockk()

    private lateinit var converter: BerlinClockConverter
    @Before
    fun setUp() {
        converter = BerlinClockConverter(berlinSeconds, berlinHours, berlinMinutes)
    }

    @Test
    fun `calculate delegates logic correctly for a standard PM time`() {
        val testTime = LocalTime.of(13, 17, 30)
        val mockSecondsLamp = Lamp(BerlinColor.YELLOW)
        val mockFiveHourRow = listOf(Lamp(BerlinColor.RED))
        val mockOneHourRow = listOf(Lamp(BerlinColor.RED))
        val mockFiveMinRow = listOf(Lamp(BerlinColor.YELLOW))
        val mockOneMinRow = listOf(Lamp(BerlinColor.YELLOW))

        every { berlinSeconds.convert(30) } returns mockSecondsLamp
        every { berlinHours.getFiveHourRow(13) } returns mockFiveHourRow
        every { berlinHours.getSingleHourRow(13) } returns mockOneHourRow
        every { berlinMinutes.getFiveMinuteRow(17) } returns mockFiveMinRow
        every { berlinMinutes.getSingleMinuteRow(17) } returns mockOneMinRow

        val result = converter.calculate(testTime)

        assertEquals(mockSecondsLamp, result.secondsLamp)
        assertEquals(mockFiveHourRow, result.fiveHourRowLamps)
        assertEquals(mockOneHourRow, result.oneHourRowLamps)
        assertEquals(mockFiveMinRow, result.fiveMinuteRowLamps)
        assertEquals(mockOneMinRow, result.oneMinuteRowLamps)
        assertEquals("13:17:30", result.digitalTime)

        verify(exactly = 1) { berlinSeconds.convert(30) }
        verify(exactly = 1) { berlinHours.getFiveHourRow(13) }
        verify(exactly = 1) { berlinHours.getSingleHourRow(13) }
        verify(exactly = 1) { berlinMinutes.getFiveMinuteRow(17) }
        verify(exactly = 1) { berlinMinutes.getSingleMinuteRow(17) }
    }

    @Test
    fun `calculate handles Midnight boundary (00-00-00) correctly`() {
        val testTime = LocalTime.MIDNIGHT
        val mockOffLamp = Lamp(BerlinColor.OFF)
        val mockOffList = emptyList<Lamp>()

        every { berlinSeconds.convert(0) } returns mockOffLamp
        every { berlinHours.getFiveHourRow(0) } returns mockOffList
        every { berlinHours.getSingleHourRow(0) } returns mockOffList
        every { berlinMinutes.getFiveMinuteRow(0) } returns mockOffList
        every { berlinMinutes.getSingleMinuteRow(0) } returns mockOffList

        val result = converter.calculate(testTime)

        assertEquals("00:00:00", result.digitalTime)
        verify { berlinHours.getFiveHourRow(0) }
        verify { berlinMinutes.getFiveMinuteRow(0) }
    }

    @Test
    fun `calculate handles End of Day boundary (23-59-59) correctly`() {
        val testTime = LocalTime.of(23, 59, 59)
        val mockLamp = Lamp(BerlinColor.RED)
        val mockList = listOf(Lamp(BerlinColor.RED))

        every { berlinSeconds.convert(59) } returns mockLamp
        every { berlinHours.getFiveHourRow(23) } returns mockList
        every { berlinHours.getSingleHourRow(23) } returns mockList
        every { berlinMinutes.getFiveMinuteRow(59) } returns mockList
        every { berlinMinutes.getSingleMinuteRow(59) } returns mockList

        val result = converter.calculate(testTime)

        assertEquals("23:59:59", result.digitalTime)
        verify { berlinSeconds.convert(59) }
        verify { berlinHours.getFiveHourRow(23) }
    }

    @Test
    fun `convert returns Y for even seconds (Midnight)`() {
        val output = converter.convert(LocalTime.of(0,0,0))
        assertEquals("Y", output.substringBefore("\n"))
    }

    @Test
    fun `convert returns O for odd seconds`() {
        val output = converter.convert(LocalTime.of(0,0,1))
        assertEquals("O", output.substringBefore("\n"))
    }

    @Test
    fun `convert returns correct 5-hour row for 00 to 04 hours`() {
        assertEquals("OOOO", getRow(1, LocalTime.of(0,0,0)))
        assertEquals("OOOO", getRow(1, LocalTime.of(4,59,59)))
    }

    @Test
    fun `convert returns correct 5-hour row for 05 to 09 hours`() {
        assertEquals("ROOO", getRow(1, LocalTime.of(5,0,0)))
        assertEquals("ROOO", getRow(1, LocalTime.of(9,59,59)))
    }

    @Test
    fun `convert returns correct 5-hour row for 10 to 14 hours`() {
        assertEquals("RROO", getRow(1, LocalTime.of(10,0,0)))
        assertEquals("RROO", getRow(1, LocalTime.of(13,59,59)))
    }

    @Test
    fun `convert returns correct 5-hour row for 15 to 19 hours`() {
        assertEquals("RROO", getRow(1, LocalTime.of(10,0,0)))
        assertEquals("RROO", getRow(1, LocalTime.of(14,59,59)))
    }

    @Test
    fun `convert returns correct 5-hour row for 20 to 23 hours`() {
        assertEquals("RRRR", getRow(1, LocalTime.of(20,0,0)))
        assertEquals("RRRR", getRow(1, LocalTime.of(23,59,59)))
    }

    @Test
    fun `convert returns correct 1-hour row for exact multiples of 5`() {
        assertEquals("OOOO", getRow(2, LocalTime.of(0,0,0)))
        assertEquals("OOOO", getRow(2, LocalTime.of(5,0,0)))
        assertEquals("OOOO", getRow(2, LocalTime.of(10,0,0)))
        assertEquals("OOOO", getRow(2, LocalTime.of(15,0,0)))
        assertEquals("OOOO", getRow(2, LocalTime.of(20,0,0)))
    }

    @Test
    fun `convert returns correct 1-hour row for remainder 1`() {
        assertEquals("ROOO", getRow(2, LocalTime.of(1,0,0)))
        assertEquals("ROOO", getRow(2, LocalTime.of(6,0,0)))
        assertEquals("ROOO", getRow(2, LocalTime.of(11,0,0)))
        assertEquals("ROOO", getRow(2, LocalTime.of(16,0,0)))
        assertEquals("ROOO", getRow(2, LocalTime.of(21,0,0)))
    }

    @Test
    fun `convert returns correct 1-hour row for remainder 2`() {
        assertEquals("RROO", getRow(2, LocalTime.of(2,0,0)))
        assertEquals("RROO", getRow(2, LocalTime.of(22,0,0)))
    }

    @Test
    fun `convert returns correct 1-hour row for remainder 3`() {
        // 3, 8, 13, 18, 23 hours
        assertEquals("RRRO", getRow(2, LocalTime.of(3,0,0)))
        assertEquals("RRRO", getRow(2, LocalTime.of(23,0,0)))
    }

    @Test
    fun `convert returns correct 1-hour row for remainder 4 (Full Row)`() {
        assertEquals("RRRR", getRow(2, LocalTime.of(14,0,0)))
        assertEquals("RRRR", getRow(2, LocalTime.of(19,0,0)))
    }

    @Test
    fun `convert returns correct 5-minute row for 00 to 04 minutes`() {
        assertEquals("OOOOOOOOOOO", getRow(3, LocalTime.of(0,0,0)))
        assertEquals("OOOOOOOOOOO", getRow(3, LocalTime.of(0,4,0)))
    }

    @Test
    fun `convert returns correct 5-minute row for 05 to 14 minutes (Yellows only)`() {
        assertEquals("YOOOOOOOOOO", getRow(3, LocalTime.of(0,5,0)))
        assertEquals("YYOOOOOOOOO", getRow(3, LocalTime.of(0,10,0)))
    }

    @Test
    fun `convert returns correct 5-minute row for 16 to 29 minutes`() {
        assertEquals("YYRYOOOOOOO", getRow(3, LocalTime.of(0,20,0)))
        assertEquals("YYRYYOOOOOO", getRow(3, LocalTime.of(0,25,0)))
    }

    @Test
    fun `convert returns correct 5-minute row for 30 minute half (Second Red)`() {
        assertEquals("YYRYYROOOOO", getRow(3, LocalTime.of(0,30,0)))
    }

    @Test
    fun `convert returns correct 5-minute row for 35 to 44 minutes`() {
        assertEquals("YYRYYRYOOOO", getRow(3, LocalTime.of(0,35,0)))
        assertEquals("YYRYYRYYOOO", getRow(3, LocalTime.of(0,40,0)))
    }

    @Test
    fun `convert returns correct 5-minute row for 45 minute quarter (Third Red)`() {
        assertEquals("YYRYYRYYROO", getRow(3, LocalTime.of(0,45,0)))
    }

    @Test
    fun `convert returns correct 5-minute row for 50 to 59 minutes (Full or almost full)`() {
        assertEquals("YYRYYRYYRYO", getRow(3, LocalTime.of(0,50,0)))
        assertEquals("YYRYYRYYRYY", getRow(3, LocalTime.of(0,55,0)))
    }

    @Test
    fun `convert returns correct 1-minute row for exact multiples of 5`() {
        assertEquals("OOOO", getRow(4, LocalTime.of(0,0,0)))
        assertEquals("OOOO", getRow(4, LocalTime.of(0,15,0)))
        assertEquals("OOOO", getRow(4, LocalTime.of(0,30,0)))
    }

    @Test
    fun `convert returns correct 1-minute row for remainder 1`() {
        assertEquals("YOOO", getRow(4, LocalTime.of(0,1,0)))
        assertEquals("YOOO", getRow(4, LocalTime.of(0,6,0)))
    }

    @Test
    fun `convert returns correct 1-minute row for remainder 2`() {
        assertEquals("YYOO", getRow(4, LocalTime.of(0,2,0)))
        assertEquals("YYOO", getRow(4, LocalTime.of(0,7,0)))
    }

    @Test
    fun `convert returns correct 1-minute row for remainder 3`() {
        assertEquals("YYYO", getRow(4, LocalTime.of(0,3,0)))
        assertEquals("YYYO", getRow(4, LocalTime.of(0,8,0)))
    }

    @Test
    fun `convert returns correct 1-minute row for remainder 4`() {
        assertEquals("YYYY", getRow(4, LocalTime.of(0,4,0)))
        assertEquals("YYYY", getRow(4, LocalTime.of(0,59,0)))
    }
    private fun getRow(index: Int, time: LocalTime): String {
        val output = converter.convert(time)
        val rows = output.split("\n")
        return if (rows.size > index) rows[index] else ""
    }
}