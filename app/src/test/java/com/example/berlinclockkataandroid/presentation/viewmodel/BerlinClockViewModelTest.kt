package com.example.berlinclockkataandroid.presentation.viewmodel

import com.example.berlinclockkataandroid.MainDispatcherRule
import com.example.berlinclockkataandroid.domain.BerlinClockConverter
import com.example.berlinclockkataandroid.domain.BerlinClockInputValidator
import com.example.berlinclockkataandroid.ui.clock.BerlinClockUiState
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BerlinClockViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val validator = mockk<BerlinClockInputValidator>(relaxed = true)
    private val converter = mockk<BerlinClockConverter>(relaxed = true)

    private lateinit var viewModel: BerlinClockViewModel

    @Before
    fun setup() {
        viewModel = BerlinClockViewModel(validator, converter)
    }

    @Test
    fun `processTimeInput emits Success state when input is valid`() = runTest {
        // Arrange
        val validTime = "12:56:01"
        val expectedOutput = "O\nRROO\nRROO\nYYRYYRYYRYY\nYOOO"

        // Mock success behavior
        coEvery { validator.isValid(validTime) } returns true
        coEvery { converter.convert(validTime) } returns expectedOutput

        viewModel = BerlinClockViewModel(validator, converter)

        // Act
        viewModel.processTimeInput(validTime)

        // Assert
        val currentState = viewModel.uiState.value
        assert(currentState is BerlinClockUiState.Success)
        assertEquals(expectedOutput, (currentState as BerlinClockUiState.Success).berlinClockOutput)

        // Verify interactions
        verify { converter.convert(validTime) }
    }
}