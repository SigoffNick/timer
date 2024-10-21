package com.example.timer.ui.timer_page

import androidx.compose.foundation.layout.Arrangement
import com.example.timer.pages.home_page.view_models.HomePageViewModel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.timer.core.Constant
import com.example.timer.core.enums.CurrentProgramState
import com.example.timer.core.enums.StopwatchState
import com.example.timer.ui.timer_page.widgets.PlayButton
import com.example.timer.ui.timer_page.widgets.TrainingTypeSwitcher


@Composable
fun TimerPage(viewModel: HomePageViewModel) {
    val context = LocalContext.current
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val fontSize = rememberSaveable(key = screenWidthDp.toString()) {
        mutableFloatStateOf(screenWidthDp * 0.18f)
    }
    val buttonMaxSize = rememberSaveable(key = screenWidthDp.toString()) {
        mutableFloatStateOf(screenWidthDp.div(3.2f))
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = getContainerColor(viewModel.currentProgramState)
        ) { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                TrainingTypeSwitcher(
                    items = Constant.PROGRAMS_LIST,
                    selectedItem = viewModel.selectedProgram,
                    onItemSelected = { viewModel.selectProgram(it, context) })
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.weight(weight = 8f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ShowTimeText(viewModel.minutes, "Minute", fontSize.floatValue)
                    ShowDot(fontSize = fontSize.floatValue)
                    ShowTimeText(viewModel.seconds, "Second", fontSize.floatValue)
                }
                Spacer(modifier = Modifier.weight(1f))
//                RoundCounter(
//                    totalRounds = viewModel.selectedProgram.value.numberOfRounds,
//                    currentRound = viewModel.currentRound.intValue
//                )
                Spacer(modifier = Modifier.height(16.dp))
                PlayButton(
                    onStop = { viewModel.stopTimer(context = context) },
                    onStart = { viewModel.startTimer(context = context) },
                    onCancel = { viewModel.cancelTimer(context = context) },
                    isRunning = viewModel.currentProgramState != StopwatchState.Idle && viewModel.currentProgramState != StopwatchState.Stopped,
                    currentProgramState = viewModel.currentProgramState
                )
            }
        }
    }
}

fun getContainerColor(timerState: StopwatchState): Color {
    return when (timerState) {
        StopwatchState.Started -> Color.Green
        StopwatchState.Canceled -> Color.Red
        StopwatchState.Idle -> Color.Black
        StopwatchState.Stopped -> Color.Black
    }
}

fun selectBackgroundColor(timerState: CurrentProgramState): Color {
    return when (timerState) {
        CurrentProgramState.WORK -> Color.Cyan
        CurrentProgramState.REST -> Color.Green
        CurrentProgramState.READY_TO_START -> Color.White
        CurrentProgramState.PREPARATION -> Color.White
    }
}

@Composable
private fun ShowTimeText(
    timeCount: String,
    label: String,
    fontSize: Float
) {
    Text(
        text = timeCount,
        style = TextStyle(
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold,
            color = if (timeCount == "00") MaterialTheme.colorScheme.onSurface.copy(0.7f) else MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
private fun ShowDot(fontSize: Float) {
    Text(
        text = ":",
        style = TextStyle(
            fontSize = (fontSize).sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface.copy(0.1f)
        )
    )
}
