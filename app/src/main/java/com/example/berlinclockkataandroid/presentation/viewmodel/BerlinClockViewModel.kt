package com.example.berlinclockkataandroid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.berlinclockkataandroid.domain.BerlinClockConverter
import com.example.berlinclockkataandroid.domain.BerlinClockInputValidator
import com.example.berlinclockkataandroid.ui.clock.BerlinClockUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class BerlinClockViewModel @Inject constructor(
    private val validator: BerlinClockInputValidator,
    private val converter: BerlinClockConverter
) : ViewModel() {
    private val _uiState = MutableStateFlow<BerlinClockUiState>(BerlinClockUiState.Initial)
    val uiState: StateFlow<BerlinClockUiState> = _uiState.asStateFlow()

    fun processTimeInput(inputTime: String) {
        viewModelScope.launch {
            _uiState.value = BerlinClockUiState.Loading

            // 1. Validate Input
            val isValid = validator.isValid(inputTime)

            if (!isValid) {
                _uiState.value = BerlinClockUiState.Error("Invalid time format. Please use HH:mm:ss")
                return@launch
            }

            // 2. Convert Time
            try {
                val result = converter.convert(inputTime)
                _uiState.value = BerlinClockUiState.Success(result)
            } catch (e: Exception) {
                // Fallback for unexpected conversion errors
                _uiState.value = BerlinClockUiState.Error("An unexpected error occurred.")
            }
        }
    }
}