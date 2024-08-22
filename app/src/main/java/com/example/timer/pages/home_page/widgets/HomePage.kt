package com.example.timer.pages.home_page.widgets

import com.example.timer.pages.home_page.view_models.HomePageViewModel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.timer.pages.home_page.view_models.TimerState
import com.example.timer.ui.theme.TimerTheme

@Composable
fun HomePage(viewModel: HomePageViewModel) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = getContainerColor(viewModel.currentTimerState.value)
        ) { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                TrainingTypeSwitcher(
                    items = viewModel.programsList,
                    selectedItem = viewModel.selectedItem.value,
                    onItemSelected = { viewModel.onItemSelected(it) })
                ProgramDescription(selectedProgram = viewModel.selectedItem.value)
                Spacer(modifier = Modifier.weight(1f))
                MainTimer(
                    time = if (viewModel.currentTimerState.value == TimerState.WORK || viewModel.currentTimerState.value == TimerState.READY_TO_START) viewModel.currentWorkTime.value else viewModel.currentRestTime.value
                )
                Spacer(modifier = Modifier.weight(1f))
                RoundCounter(
                    totalRounds = viewModel.selectedItem.value.numberOfRounds,
                    currentRound = viewModel.currentRound.value
                )
                Spacer(modifier = Modifier.height(16.dp))
                PlayButton(
                    onStop = { viewModel.stopTimer() },
                    onSwitch = { viewModel.startUITimer() },
                    isActive = viewModel.isActive.value,
                    timerState = viewModel.currentTimerState.value
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

fun getContainerColor(timerState: TimerState): Color {
    return when (timerState) {
        TimerState.WORK -> Color.Green
        TimerState.REST -> Color.Red
        TimerState.READY_TO_START -> Color.Black
        TimerState.PREPARATION -> Color.Black
    }
}

fun selectBackgroundColor(timerState: TimerState): Color {
    return when (timerState) {
        TimerState.WORK -> Color.Cyan
        TimerState.REST -> Color.Green
        TimerState.READY_TO_START -> Color.White
        TimerState.PREPARATION -> Color.White
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    val viewModel = HomePageViewModel()
    TimerTheme {
        HomePage(viewModel)
    }
}