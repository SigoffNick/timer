package com.example.timer.features.timer.ui

import com.example.timer.features.timer.view_models.TimerPageViewModel

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.timer.core.Constant
import com.example.timer.core.enums.StopwatchState
import com.example.timer.core.training_programs.program_step.PreparationStep
import com.example.timer.core.training_programs.program_step.ProgramStep
import com.example.timer.core.training_programs.program_step.RestStep
import com.example.timer.core.training_programs.program_step.WorkStep
import com.example.timer.features.timer.ui.widgets.PlayButton
import com.example.timer.features.timer.ui.widgets.ProgramInfo
import com.example.timer.features.timer.ui.widgets.RoundCounter
import com.example.timer.features.timer.ui.widgets.Timer
import com.example.timer.features.timer.ui.widgets.TrainingTypeSwitcher


@Composable
fun TimerPage(viewModel: TimerPageViewModel) {
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = selectBackgroundColor(viewModel.currentStep)
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            TrainingTypeSwitcher(
                items = Constant.PROGRAMS_LIST,
                selectedItem = viewModel.selectedProgram,
                onItemSelected = { viewModel.selectProgram(it, context) })
            ProgramInfo(
                workTime = viewModel.currentStep.duration,
                restTime = viewModel.currentStep.duration
            )
            Spacer(modifier = Modifier.weight(1f))
            Timer(duration = viewModel.duration)
            Spacer(modifier = Modifier.weight(1f))
            RoundCounter(
                totalRounds = viewModel.numberOfRounds,
                currentRound = viewModel.currentRound
            )
            Spacer(modifier = Modifier.height(32.dp))
            PlayButton(
                onStop = { viewModel.stopTimer(context = context) },
                onStart = { viewModel.startTimer(context = context) },
                onCancel = { viewModel.cancelTimer(context = context) },
                isRunning = viewModel.currentProgramState != StopwatchState.Idle && viewModel.currentProgramState != StopwatchState.Stopped,
                currentProgramState = viewModel.currentProgramState
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

fun selectBackgroundColor(programStep: ProgramStep): Color {
    return when (programStep) {
        is PreparationStep -> Color.Black
        is WorkStep -> Color.Green
        is RestStep -> Color.Red
        else -> Color.Black
    }
}
