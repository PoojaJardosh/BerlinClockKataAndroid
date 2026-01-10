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
}