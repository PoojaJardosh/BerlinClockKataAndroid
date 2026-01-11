package com.example.berlinclockkataandroid.ui.clock

sealed interface BerlinClockUiState {
    data object Initial : BerlinClockUiState
    data object Loading : BerlinClockUiState
    data class Success(val berlinClockOutput: String) : BerlinClockUiState
    data class Error(val message: String) : BerlinClockUiState
}