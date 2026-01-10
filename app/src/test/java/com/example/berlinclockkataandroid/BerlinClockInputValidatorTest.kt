package com.example.berlinclockkataandroid

import com.example.berlinclockkataandroid.domain.BerlinClockInputValidator
import junit.framework.TestCase.assertTrue
import org.junit.Test

class BerlinClockInputValidatorTest {
    private val validator = BerlinClockInputValidator()

    @Test
    fun `isValid returns true for non-empty string`() {
        assertTrue(validator.isValid("12:56:01"))
    }
}