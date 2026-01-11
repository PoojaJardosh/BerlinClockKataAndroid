package com.example.berlinclockkataandroid.ui.clock

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.berlinclockkataandroid.presentation.viewmodel.BerlinClockViewModel

@Composable
fun BerlinClockRoute(
    viewModel: BerlinClockViewModel = hiltViewModel()
) {
    // Collect state in a lifecycle-aware manner
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BerlinClockScreen(
        uiState = uiState,
        onConvertClick = { inputTime -> viewModel.processTimeInput(inputTime) }
    )
}

@Composable
fun BerlinClockScreen(
    uiState: BerlinClockUiState,
    onConvertClick: (String) -> Unit
){
    var timeInput by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = timeInput,
            onValueChange = { timeInput = it },
            label = { Text("Enter Time (HH:MM:SS)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {onConvertClick(timeInput)},
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState !is BerlinClockUiState.Loading
        ) {
            Text("Convert to Berlin Clock")
        }

        Spacer(modifier = Modifier.height(24.dp))

        when(uiState){
            is BerlinClockUiState.Loading -> {
                CircularProgressIndicator(
                )
            }
            is BerlinClockUiState.Success -> {
                BerlinClockDisplay(output = uiState.berlinClockOutput)
            }
            is BerlinClockUiState.Error -> {
                Text(text = uiState.message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge)
            }
            is BerlinClockUiState.Initial -> {
                Text(text= "Enter a time to see the berlin clock.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun BerlinClockDisplay(output: String) {
    val scrollState = rememberScrollState()
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
                .fillMaxWidth()
                .verticalScroll(scrollState),
        ) {
            Text(
                text = "Berlin Clock Output:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = output,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp),
                fontFamily = FontFamily.Monospace,
                lineHeight = 40.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}