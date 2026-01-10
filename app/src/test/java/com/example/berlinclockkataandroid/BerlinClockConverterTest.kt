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

}