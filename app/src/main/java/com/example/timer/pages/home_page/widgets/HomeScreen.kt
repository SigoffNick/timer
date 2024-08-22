package com.example.timer.pages.home_page.widgets

import MainTimer
import com.example.timer.pages.home_page.view_models.HomeViewModel
import PlayButton
import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.timer.R
import com.example.timer.pages.home_page.view_models.TimerState
import com.example.timer.ui.theme.TimerTheme
import kotlin.time.Duration

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_image),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .blur(8.dp),
        )
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent
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
                ResetRoundButton { viewModel.stopTimer() }
                Spacer(modifier = Modifier.weight(1f))
                MainTimer(
                    color = selectBackgroundColor(viewModel.currentTimerState.value),
                    progress = viewModel.progress.floatValue,
                    currentRound = viewModel.currentRound.intValue,
                    totalRounds = viewModel.selectedItem.value.numberOfRounds,
                    time = getCurrentTim(viewModel)
                )
                Spacer(modifier = Modifier.weight(1f))
                PlayButton(
                    onSwitch = { viewModel.startUITimer() },
                    isActive = viewModel.isPlaying.value
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

fun getCurrentTim(viewModel: HomeViewModel): Duration {
    return when (viewModel.currentTimerState.value) {
        TimerState.WORK -> viewModel.currentWorkTime.value
        TimerState.REST -> viewModel.currentRestTime.value
        TimerState.PREPARATION -> viewModel.currentPreparationTime.value
        TimerState.READY_TO_START -> viewModel.selectedItem.value.workDuration
    }
}


fun selectBackgroundColor(timerState: TimerState): Color {
    return when (timerState) {
        TimerState.WORK -> Color.Cyan
        TimerState.REST -> Color.Green
        TimerState.READY_TO_START -> Color.White
        TimerState.PREPARATION -> Color.Yellow
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    val viewModel = HomeViewModel()
    TimerTheme {
        HomeScreen(viewModel)
    }
}