package com.example.berlinclockkataandroid.ui.clock

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.berlinclockkataandroid.presentation.mapper.BerlinColor
import com.example.berlinclockkataandroid.presentation.mapper.UiColorMapper
import com.example.berlinclockkataandroid.presentation.viewmodel.BerlinClockViewModel

fun BerlinColor.toComposeColor(): Color {
    return when (this) {
        BerlinColor.RED -> Color.Red
        BerlinColor.YELLOW -> Color.Yellow
        BerlinColor.OFF -> Color.Gray
    }
}

@Composable
fun BerlinClockScreen(
    viewModel: BerlinClockViewModel = hiltViewModel(),
){
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = state.digitalTime,
            color = Color.White,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        SecondsLamp(isOn = state.secondsLamp)
        Spacer(modifier = Modifier.height(16.dp))
        DynamicLampRow(pattern = state.fiveHourRow)
        Spacer(modifier = Modifier.height(8.dp))
        DynamicLampRow(pattern = state.oneHourRow)
        Spacer(modifier = Modifier.height(8.dp))
        DynamicLampRow(pattern = state.fiveMinuteRow)
        Spacer(modifier = Modifier.height(8.dp))
        DynamicLampRow(pattern = state.oneMinuteRow)

        Spacer(modifier = Modifier.height(24.dp))
        Text(text = state.birlinClockString,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun DynamicLampRow(pattern: String) {
    val colors = UiColorMapper.map(pattern)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        colors.forEach { berlinColor ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(berlinColor.toComposeColor())
                    .border(1.dp, Color.Black, RoundedCornerShape(4.dp))
            )
        }
    }
}

@Composable
fun SecondsLamp(isOn: String) {
    val color = if (isOn == "Y") Color.Yellow else Color.Gray
    Box(
        modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(color)
            .border(2.dp, Color.Black, CircleShape)
    )
}

