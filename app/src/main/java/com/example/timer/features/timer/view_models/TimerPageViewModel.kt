package com.example.timer.features.timer.view_models

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import com.example.timer.core.enums.ServiceAction
import com.example.timer.core.training_programs.program_step.PreparationStep
import com.example.timer.core.training_programs.program_step.WorkStep
import com.example.timer.features.timer.services.ServiceHelper
import com.example.timer.features.timer.services.StopwatchService

/**
 * The HomePageViewModel class is used to manage the data for the HomePage.
 */
class TimerPageViewModel(stopwatchService: StopwatchService) : ViewModel() {

    val currentProgramState by stopwatchService.currentState

    val selectedProgram by stopwatchService.selectedProgram

    val duration by stopwatchService.duration

    val currentStep by stopwatchService.currentStep

    val numberOfRounds get() = selectedProgram.programFlow.countElementsOfType<WorkStep>()

    val currentRound get() = selectedProgram.programFlow.indexOf(currentStep) / 2 + (if (currentStep is WorkStep || currentStep is PreparationStep) 1 else 0)

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

    inline fun <reified T> List<*>.countElementsOfType(): Int {
        return this.count { it is T }
    }
}
