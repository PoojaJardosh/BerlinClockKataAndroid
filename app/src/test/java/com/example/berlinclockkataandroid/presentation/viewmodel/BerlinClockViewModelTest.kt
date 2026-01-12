package com.example.berlinclockkataandroid.presentation.viewmodel

import app.cash.turbine.test
import com.example.berlinclockkataandroid.MainDispatcherRule
import com.example.berlinclockkataandroid.domain.BerlinClockConverter
import com.example.berlinclockkataandroid.domain.TimeProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalTime
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BerlinClockViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val timeProvider = mockk<TimeProvider>(relaxed = true)
    private val converter = mockk<BerlinClockConverter>(relaxed = true)

    private lateinit var viewModel: BerlinClockViewModel

    @Before
    fun setup() {
        every { timeProvider.getCurrentTime() } returns LocalTime.of(0, 0, 0)
        every { converter.convert(any()) } returns "Y\nOOOO\nOOOO\nOOOOOOOOOOO\nOOOO"
        viewModel = BerlinClockViewModel(timeProvider, converter)
    }

    @Test
    fun `processTimeInput emits Success state when time updates`() = runTest {
        val testTime = LocalTime.of(12, 56, 1)
        val validTime = "12:56:01"
        val expectedOutput = "O\nRROO\nRROO\nYYRYYRYYRYY\nYOOO"
        every { converter.convert(validTime) } returns expectedOutput

       viewModel.uiState.test {
           skipItems(1)
           viewModel.updateClock(testTime)
           val updatedState = awaitItem()
           assertEquals("O", updatedState.secondsLamp)
           assertEquals("RROO", updatedState.fiveHourRow)
           assertEquals("RROO", updatedState.oneHourRow)
           assertEquals("YYRYYRYYRYY", updatedState.fiveMinuteRow)
           assertEquals("YOOO", updatedState.oneMinuteRow)
           assertEquals("12:56:01", updatedState.digitalTime)
           expectNoEvents()
       }
    }

    @Test
    fun `should update continuously every second`() = runTest {
        val time1 = LocalTime.of(10, 0, 0)
        val time2 = LocalTime.of(10, 0, 1)
        every { timeProvider.getCurrentTime() } returnsMany listOf(time1, time2)
        every { converter.convert("10:00:00") } returns "Y\nRROO\nOOOO\nOOOOOOOOOOO\nOOOO"
        every { converter.convert("10:00:01") } returns "O\nRROO\nOOOO\nOOOOOOOOOOO\nOOOO"
        viewModel.uiState.test {
            awaitItem()
            advanceTimeBy(1005L)
            val secondEmission = awaitItem()
            assertEquals("10:00:01", secondEmission.digitalTime)
            verify(atLeast = 2) { timeProvider.getCurrentTime() }
        }
    }
}