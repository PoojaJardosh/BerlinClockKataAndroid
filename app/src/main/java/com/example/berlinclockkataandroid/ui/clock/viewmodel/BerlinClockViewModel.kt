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
import javax.inject.Inject

@HiltViewModel
class BerlinClockViewModel @Inject constructor(
    private val timeProvider: TimeProvider,
    private val converter: BerlinClockConverter
) : ViewModel() {
    private val _clockState = MutableStateFlow(converter.calculate(timeProvider.getCurrentTime()))
    val clockState: StateFlow<BerlinClockState> = _clockState.asStateFlow()

    init {
        startTicking()
    }

    private fun startTicking() {
        viewModelScope.launch {
            while (isActive) {
                delay(1000L)
                _clockState.update {
                    converter.calculate(timeProvider.getCurrentTime())
                }
            }
        }
    }
}
