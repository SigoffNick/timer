package com.example.timer.experimental.home_page_experimental_design.widgets

import MainTimer
import com.example.timer.experimental.home_page_experimental_design.view_models.HomePageExperimentalDesignViewModel
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
import com.example.timer.core.theme.StopWatchTheme
import kotlin.time.Duration

@Composable
fun HomePageExperimentalDesign(viewModel: HomePageExperimentalDesignViewModel) {
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
                Spacer(modifier = Modifier.height(16.dp))
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

fun getCurrentTim(viewModel: HomePageExperimentalDesignViewModel): Duration {
    return when (viewModel.currentTimerState.value) {
        TimerState.WORK -> viewModel.currentWorkTime.value
        TimerState.REST -> viewModel.currentRestTime.value
        TimerState.PREPARATION -> viewModel.currentPreparationTime.value
        TimerState.READY_TO_START -> viewModel.selectedItem.value.workDuration
    }
}


fun selectBackgroundColor(timerState: TimerState): Color {
    return when (timerState) {
        TimerState.WORK -> Color.Red.copy(alpha = 0.5f)
        TimerState.REST -> Color.Green.copy(alpha = 0.5f)
        TimerState.READY_TO_START -> Color.White
        TimerState.PREPARATION -> Color.Yellow.copy(alpha = 0.5f)
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    val viewModel = HomePageExperimentalDesignViewModel()
    StopWatchTheme {
        HomePageExperimentalDesign(viewModel)
    }
}