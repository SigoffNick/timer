package com.example.timer.pages.home_page.view_models

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import com.example.timer.core.enums.ServiceAction
import com.example.timer.core.enums.CurrentProgramState
import com.example.timer.core.enums.StopwatchState
import com.example.timer.core.training_programs.AmateurBoxingProgram
import com.example.timer.core.training_programs.BoxingProgram
import com.example.timer.core.training_programs.ClassicBoxingProgram
import com.example.timer.core.training_programs.TestingBoxingProgram
import com.example.timer.service.ServiceHelper
import com.example.timer.service.StopwatchService

class HomePageViewModel(stopwatchService: StopwatchService) : ViewModel() {
    val programsList = listOf(
        TestingBoxingProgram(),
        ClassicBoxingProgram(),
        AmateurBoxingProgram(),
    )
    private val _selectedProgram = mutableStateOf(programsList[0])
    val selectedProgram: State<BoxingProgram> = _selectedProgram
    val currentRound = mutableIntStateOf(0)
    val currentProgramState = mutableStateOf(CurrentProgramState.READY_TO_START)

    val minutes by stopwatchService.minutes
    val seconds by stopwatchService.seconds

    val isRunning = stopwatchService.currentState.value != StopwatchState.Idle

    private var programStep = 0;


    fun onItemSelected(item: BoxingProgram) {
        _selectedProgram.value = item
        currentRound.intValue = 0
        currentProgramState.value = CurrentProgramState.READY_TO_START
    }

    fun startTimer(context: Context) {
        if (currentProgramState.value == CurrentProgramState.READY_TO_START) {
            currentProgramState.value = CurrentProgramState.PREPARATION
        }
        ServiceHelper.triggerForegroundService(
            context = context,
            action = ServiceAction.ACTION_SERVICE_START,
            seconds = _selectedProgram.value.programFlow[programStep].inWholeSeconds.toInt()
        )
    }

    fun stopTimer(context: Context) {
        ServiceHelper.triggerForegroundService(
            context = context,
            action = ServiceAction.ACTION_SERVICE_STOP,
            seconds = _selectedProgram.value.programFlow[programStep].inWholeSeconds.toInt()
        )
    }

    fun cancelTimer(context: Context) {
        ServiceHelper.triggerForegroundService(
            context = context,
            action = ServiceAction.ACTION_SERVICE_CANCEL,
            seconds = _selectedProgram.value.programFlow[programStep].inWholeSeconds.toInt()
        )
    }
}
