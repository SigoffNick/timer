package com.example.timer.pages.home_page.view_models

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import com.example.timer.core.Constant
import com.example.timer.core.training_programs.AmateurBoxingProgram
import com.example.timer.core.training_programs.BoxingProgram
import com.example.timer.core.training_programs.ClassicBoxingProgram
import com.example.timer.core.training_programs.TestingBoxingProgram
import com.example.timer.service.ServiceHelper
import com.example.timer.service.StopwatchService
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@OptIn(ExperimentalAnimationApi::class)
class HomePageViewModel(stopwatchService: StopwatchService) : ViewModel() {
    val programsList = listOf(
        TestingBoxingProgram(),
        ClassicBoxingProgram(),
        AmateurBoxingProgram(),
    )
    private val _selectedItem = mutableStateOf(programsList[0])
    private var uiTimer: CustomTimer? = null
    val selectedItem: State<BoxingProgram> = _selectedItem
    val currentRound = mutableIntStateOf(0)
    val currentTimerState = mutableStateOf(TimerState.READY_TO_START)
    val hours by stopwatchService.hours
    val minutes by stopwatchService.minutes
    val seconds by stopwatchService.seconds


    fun onItemSelected(item: BoxingProgram) {
        _selectedItem.value = item
        currentRound.intValue = 0
        currentTimerState.value = TimerState.READY_TO_START
    }

    fun startTimer(context: Context) {
        ServiceHelper.triggerForegroundService(
            context = context, action = Constant.ACTION_SERVICE_START
        )
    }

    fun stopTimer(context: Context) {
        ServiceHelper.triggerForegroundService(
            context = context, action = Constant.ACTION_SERVICE_STOP
        )
    }

    fun cancelTimer(context: Context) {
        ServiceHelper.triggerForegroundService(
            context = context, action = Constant.ACTION_SERVICE_CANCEL
        )
    }
}
