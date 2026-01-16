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
import com.example.berlinclockkataandroid.ui.clock.viewmodel.BerlinClockViewModel

fun BerlinColor.toComposeColor(): Color {
    return when (this) {
        BerlinColor.RED -> Color.Red
        BerlinColor.YELLOW -> Color.Yellow
        BerlinColor.OFF -> Color.Gray
    }
}

@Composable
fun RowScope.LampBox(color: BerlinColor, weight: Float) {
    Box(
        modifier = Modifier
            .height(32.dp)
            .weight(weight)
            .padding(horizontal = 2.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(color.toComposeColor())
            .border(
                width = 1.dp,
                color = Color.DarkGray,
                shape = RoundedCornerShape(4.dp)
            )
    )
}

@Composable
fun SecondsCircle(lamp: Lamp) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(lamp.color.toComposeColor())
            .border(
                width = 2.dp,
                color = Color.DarkGray,
                shape = CircleShape
            )
    )
}

@Composable
fun BerlinClockScreen(
    viewModel: BerlinClockViewModel = hiltViewModel(),
){
    val clockState by viewModel.clockState.collectAsState()
    clockState.let { clock ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = clock.digitalTime,
                color = Color.White,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            SecondsCircle(clock.secondsLamp)
            Spacer(modifier = Modifier.height(24.dp))
            LampRow(clock.fiveHourRowLamps)
            Spacer(modifier = Modifier.height(24.dp))
            LampRow(clock.oneHourRowLamps)
            Spacer(modifier = Modifier.height(24.dp))
            LampRow(clock.fiveMinuteRowLamps)
            Spacer(modifier = Modifier.height(24.dp))
            LampRow(clock.oneMinuteRowLamps)
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = clock.birlinClockString,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
@Composable
fun LampRow(lamps: List<Lamp>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        lamps.forEach { lamp ->
            LampBox(color = lamp.color, weight = 1f)
        }
    }
}
