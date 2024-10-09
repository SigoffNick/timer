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
import com.example.timer.pages.home_page.view_models.TimerState
import com.example.timer.ui.timer_page.widgets.PlayButton
import com.example.timer.ui.timer_page.widgets.RoundCounter
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
                RoundCounter(
                    totalRounds = viewModel.selectedItem.value.numberOfRounds,
                    currentRound = viewModel.currentRound.intValue
                )
                Spacer(modifier = Modifier.height(16.dp))
                PlayButton(
                    onStop = {viewModel.cancelTimer(context = context) },
                    onSwitch = {viewModel.startTimer(context = context) },
                    isActive = false,
                    timerState = viewModel.currentTimerState.value
                )
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
