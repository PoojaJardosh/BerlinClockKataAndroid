package com.example.berlinclockkataandroid

import com.example.berlinclockkataandroid.domain.BerlinClockInputValidator
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class BerlinClockInputValidatorTest {
    private val validator = BerlinClockInputValidator()

    @Test
    fun `isValid returns true for non-empty string`() {
        assertTrue(validator.isValid("12:56:01"))
    }

    @Test
    fun `isValid returns false for empty string`() {
        assertFalse(validator.isValid(""))
    }

    @Test
    fun `isValid returns false for non-numeric characters`() {
        assertFalse(validator.isValid("12:00:xx"))
    }

    @Test
    fun `isValid returns false for missing seconds segment`() {
        assertFalse(validator.isValid("12:00"))
    }

    @Test
    fun `isValid returns false for wrong separator`() {
        assertFalse(validator.isValid("12-56-00"))
        assertFalse(validator.isValid("12 56 00"))
    }

    @Test
    fun `isValid returns false for single digit format without padding`() {
        // The requirement specifies 'hh:mm:ss' which implies "01" not "1"
        assertFalse(validator.isValid("1:2:3"))
    }

    @Test
    fun `isValid returns true for standard valid time`() {
        assertTrue(validator.isValid("12:56:01"))
    }

    @Test
    fun `isValid returns true for midnight`() {
        assertTrue(validator.isValid("00:00:00"))
    }
}