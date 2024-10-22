package com.example.timer.pages.home_page.view_models

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import com.example.timer.core.enums.ServiceAction
import com.example.timer.service.ServiceHelper
import com.example.timer.service.StopwatchService

/**
 * The HomePageViewModel class is used to manage the data for the HomePage.
 */
class HomePageViewModel(stopwatchService: StopwatchService) : ViewModel() {

    val currentProgramState by stopwatchService.currentState

    val selectedProgram by stopwatchService.selectedProgram

    val duration by stopwatchService.duration

    val currentStep by stopwatchService.currentStep

    fun selectProgram(programIndex: Int, context: Context) {
        ServiceHelper.triggerForegroundService(
            context = context,
            action = ServiceAction.ACTION_SERVICE_SET_PROGRAM,
            programIndex = programIndex
        )
    }

    fun startTimer(context: Context) {
        ServiceHelper.triggerForegroundService(
            context = context,
            action = ServiceAction.ACTION_SERVICE_START_PROGRAM,
        )
    }

    fun stopTimer(context: Context) {
        ServiceHelper.triggerForegroundService(
            context = context,
            action = ServiceAction.ACTION_SERVICE_STOP_PROGRAM,
        )
    }

    fun cancelTimer(context: Context) {
        ServiceHelper.triggerForegroundService(
            context = context,
            action = ServiceAction.ACTION_SERVICE_CANCEL_PROGRAM,
        )
    }
}
