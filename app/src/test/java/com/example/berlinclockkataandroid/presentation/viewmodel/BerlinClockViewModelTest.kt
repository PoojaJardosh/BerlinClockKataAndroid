package com.example.berlinclockkataandroid.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import app.cash.turbine.test
import com.example.berlinclockkataandroid.MainDispatcherRule
import com.example.berlinclockkataandroid.domain.BerlinClockConverter
import com.example.berlinclockkataandroid.domain.TimeProvider
import com.example.berlinclockkataandroid.ui.clock.BerlinClockConstants.FIVE_HOURS_LAMP_COUNT
import com.example.berlinclockkataandroid.ui.clock.BerlinClockConstants.FIVE_MINUTES_LAMP_COUNT
import com.example.berlinclockkataandroid.ui.clock.BerlinClockConstants.SINGLE_HOUR_LAMP_COUNT
import com.example.berlinclockkataandroid.ui.clock.BerlinClockConstants.SINGLE_MINUTE_LAMP_COUNT
import com.example.berlinclockkataandroid.ui.clock.BerlinClockConstants.TICK_DELAY_MS
import com.example.berlinclockkataandroid.ui.clock.BerlinClockState
import com.example.berlinclockkataandroid.ui.clock.BerlinColor
import com.example.berlinclockkataandroid.ui.clock.Lamp
import com.example.berlinclockkataandroid.ui.clock.viewmodel.BerlinClockViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.cancel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BerlinClockViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private val timeProvider: TimeProvider = mockk()
    private val converter: BerlinClockConverter = mockk()

    private lateinit var viewModel: BerlinClockViewModel
    private lateinit var mockState1: BerlinClockState
    private lateinit var mockState2: BerlinClockState

    private val fourOffLamps = List(FIVE_HOURS_LAMP_COUNT) { Lamp(BerlinColor.OFF) }
    private val elevenOffLamps = List(FIVE_MINUTES_LAMP_COUNT) { Lamp(BerlinColor.OFF) }


@Before
fun setup() {
   mockState1 = createMockState("11:12:08")
   mockState2 = createMockState("11:12:09")

   every { timeProvider.getCurrentTime() } returns LocalTime.of(11, 12, 8)
   every { converter.calculate(any()) } returns mockState1
}

private fun createMockState(time: String) = BerlinClockState(
    secondsLamp = Lamp(BerlinColor.YELLOW),
    fiveHourRowLamps = List(FIVE_HOURS_LAMP_COUNT) { Lamp(BerlinColor.RED) },
    oneHourRowLamps = List(SINGLE_HOUR_LAMP_COUNT) { Lamp(BerlinColor.RED) },
    fiveMinuteRowLamps = List(FIVE_MINUTES_LAMP_COUNT) { Lamp(BerlinColor.YELLOW) },
    oneMinuteRowLamps = List(SINGLE_MINUTE_LAMP_COUNT) { Lamp(BerlinColor.YELLOW) },
    digitalTime = time,
    birlinClockString = "Y\nRROO\nROOO\nYYOOOOOOOOO\nYYOO"
)

@Test
fun `init starts ticking and emits initial state immediately`() = runTest(testDispatcher) {
   viewModel = BerlinClockViewModel(timeProvider, converter)

   assertEquals(mockState1, viewModel.clockState.value)
    viewModel.viewModelScope.cancel()
}

@Test
fun `clock state updates every second`() = runTest(testDispatcher) {
   val initialTime = LocalTime.of(12, 0, 0)
   val nextTime = LocalTime.of(12, 0, 1)
   val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

   val initialState = BerlinClockState(
       secondsLamp = Lamp(BerlinColor.YELLOW),
       fiveHourRowLamps = listOf(
           Lamp(BerlinColor.RED), Lamp(BerlinColor.RED),
           Lamp(BerlinColor.OFF), Lamp(BerlinColor.OFF)
       ),
       oneHourRowLamps = listOf(
           Lamp(BerlinColor.RED), Lamp(BerlinColor.RED),
           Lamp(BerlinColor.OFF), Lamp(BerlinColor.OFF)
       ),
       fiveMinuteRowLamps = elevenOffLamps,
       oneMinuteRowLamps = fourOffLamps,
       digitalTime = initialTime.format(timeFormatter), // "12:00:00"
       birlinClockString = "Y\nRROO\nRROO\nOOOOOOOOOOO\nOOOO"
   )

   val nextState = BerlinClockState(
       secondsLamp = Lamp(BerlinColor.OFF),
       fiveHourRowLamps = listOf(
           Lamp(BerlinColor.RED), Lamp(BerlinColor.RED),
           Lamp(BerlinColor.OFF), Lamp(BerlinColor.OFF)
       ),
       oneHourRowLamps = listOf(
           Lamp(BerlinColor.RED), Lamp(BerlinColor.RED),
           Lamp(BerlinColor.OFF), Lamp(BerlinColor.OFF)
       ),
       fiveMinuteRowLamps = elevenOffLamps,
       oneMinuteRowLamps = fourOffLamps,
       digitalTime = nextTime.format(timeFormatter), // "12:00:01"
       birlinClockString = "O\nRROO\nRROO\nOOOOOOOOOOO\nOOOO" // Fixed separators
   )

   every { timeProvider.getCurrentTime() } returnsMany listOf(initialTime, nextTime)
   every { converter.calculate(initialTime) } returns initialState
   every { converter.calculate(nextTime) } returns nextState

   val viewModel = BerlinClockViewModel(timeProvider, converter)

   viewModel.clockState.test {
       assertEquals(initialState, awaitItem())
       testScheduler.advanceTimeBy(TICK_DELAY_MS)
       assertEquals(nextState, awaitItem())
       cancelAndIgnoreRemainingEvents()
   }
    viewModel.viewModelScope.cancel()
}
}
