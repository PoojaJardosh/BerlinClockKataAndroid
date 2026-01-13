package com.example.berlinclockkataandroid.presentation.viewmodel

import app.cash.turbine.test
import com.example.berlinclockkataandroid.MainDispatcherRule
import com.example.berlinclockkataandroid.domain.BerlinClockConverter
import com.example.berlinclockkataandroid.domain.TimeProvider
import com.example.berlinclockkataandroid.ui.clock.viewmodel.BerlinClockViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalTime
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BerlinClockViewModelTest {
    
    private val testDispatcher = StandardTestDispatcher()
    
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)
    
    private val timeProvider = mockk<TimeProvider>(relaxed = true)
    private val converter = mockk<BerlinClockConverter>(relaxed = true)

    private lateinit var viewModel: BerlinClockViewModel

    @Before
    fun setup() {
        every { timeProvider.getCurrentTime() } returns LocalTime.of(0, 0, 0)
        every { converter.convert(any()) } returns "Y\nOOOO\nOOOO\nOOOOOOOOOOO\nOOOO"
    }

    @Test
    fun `updateClock updates state correctly`() = runTest {
        viewModel = BerlinClockViewModel(timeProvider, converter)
        val testTime = LocalTime.of(12, 56, 1)
        val validTime = testTime.toString()
        val expectedOutput = "O\nRROO\nRROO\nYYRYYRYYRYY\nYOOO"
        every { converter.convert(validTime) } returns expectedOutput

        viewModel.uiState.test {
            assertEquals("00:00:00", awaitItem().digitalTime)
            viewModel.updateClock(testTime)
            val updatedState = awaitItem()
            assertEquals("O", updatedState.secondsLamp)
            assertEquals("12:56:01", updatedState.digitalTime)
        }
    }

    @Test
    fun `should update continuously every second`() = runTest {
        val time1 = LocalTime.of(10, 0, 0)
        val time2 = LocalTime.of(10, 0, 1)
        every { timeProvider.getCurrentTime() }  returns time1 andThen time2 andThen time2
        every { converter.convert("10:00:00") } returns "Y\nRROO\nOOOO\nOOOOOOOOOOO\nOOOO"
        every { converter.convert("10:00:01") } returns "O\nRROO\nOOOO\nOOOOOOOOOOO\nOOOO"
        viewModel = BerlinClockViewModel(timeProvider, converter)
        viewModel.uiState.test {
            assertEquals("00:00:00", awaitItem().digitalTime)
            advanceTimeBy(1000L)
            val firstTick = awaitItem()
            assert(firstTick.digitalTime.startsWith("10:00"))
            advanceTimeBy(1000L)
            val secondTick = awaitItem()
            assert(secondTick.digitalTime.startsWith("10:00"))
            cancelAndIgnoreRemainingEvents()
        }
        verify(atLeast = 2) { timeProvider.getCurrentTime() }
    }
}
