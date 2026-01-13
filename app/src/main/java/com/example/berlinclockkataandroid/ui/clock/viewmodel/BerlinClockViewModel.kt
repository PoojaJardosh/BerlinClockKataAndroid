package com.example.berlinclockkataandroid.ui.clock.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.berlinclockkataandroid.domain.BerlinClockConverter
import com.example.berlinclockkataandroid.domain.TimeProvider
import com.example.berlinclockkataandroid.ui.clock.BerlinClockState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class BerlinClockViewModel @Inject constructor(
    private val timeProvider: TimeProvider,
    private val converter: BerlinClockConverter
) : ViewModel() {
    private val _state = MutableStateFlow(BerlinClockState())
    val uiState: StateFlow<BerlinClockState> = _state.asStateFlow()

    init {
        startTicking()
    }

    private fun startTicking() {
        viewModelScope.launch {
            while (isActive) {
                updateClock(timeProvider.getCurrentTime())
                delay(1000L)
            }
        }
    }

    fun updateClock(localTime: LocalTime) {
        val timeString = localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))

        val rawBerlinString = converter.convert(timeString)

        val rows = rawBerlinString.split("\n")

        if (rows.size == 5) {
            _state.update {
                it.copy(
                    secondsLamp = rows[0],
                    fiveHourRow = rows[1],
                    oneHourRow = rows[2],
                    fiveMinuteRow = rows[3],
                    oneMinuteRow = rows[4],
                    digitalTime = timeString,
                    birlinClockString = rawBerlinString
                )
            }
        }
    }
}