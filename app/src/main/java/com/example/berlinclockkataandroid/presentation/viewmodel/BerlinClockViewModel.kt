package com.example.berlinclockkataandroid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.berlinclockkataandroid.domain.BerlinClockConverter
import com.example.berlinclockkataandroid.domain.BerlinClockInputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BerlinClockViewModel @Inject constructor(
    private val validator: BerlinClockInputValidator,
    private val converter: BerlinClockConverter
) : ViewModel() {
}