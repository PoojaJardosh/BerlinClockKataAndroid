package com.example.berlinclockkataandroid.presentation.viewmodel

import com.example.berlinclockkataandroid.MainDispatcherRule
import com.example.berlinclockkataandroid.domain.BerlinClockConverter
import com.example.berlinclockkataandroid.domain.BerlinClockInputValidator
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule

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
}