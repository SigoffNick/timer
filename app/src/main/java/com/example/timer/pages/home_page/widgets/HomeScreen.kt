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

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_image),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize().blur(8.dp),
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
                Spacer(modifier = Modifier.weight(1f))
                MainTimer(
                    color = selectBackgroundColor(viewModel.timerState.value),
                    progress = viewModel.progress.floatValue,
                    currentRound = viewModel.currentRound.intValue,
                    totalRounds = viewModel.selectedItem.value.numberOfRounds,
                    time = if (viewModel.timerState.value == TimerState.WORK || viewModel.timerState.value == TimerState.READY_TO_START) viewModel.currentWorkTime.value else viewModel.currentRestTime.value
                )
                Spacer(modifier = Modifier.weight(1f))
                PlayButton(onClick = { viewModel.switchTimer() }, viewModel.isPlaying.value)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

fun selectBackgroundColor(timerState: TimerState): Color {
    return when (timerState) {
        TimerState.WORK -> Color.Cyan
        TimerState.REST -> Color.Green
        TimerState.READY_TO_START -> Color.White
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