package com.example.timer.pages.home_page.view_models

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.viewModelScope
import com.example.timer.pages.home_page.training_programs.AmateurBoxingProgram
import com.example.timer.pages.home_page.training_programs.BoxingProgram
import com.example.timer.pages.home_page.training_programs.ClassicBoxingProgram
import com.example.timer.pages.home_page.training_programs.TestingBoxingProgram
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class HomePageViewModel : ViewModel() {
    val programsList = listOf(
        TestingBoxingProgram(),
        ClassicBoxingProgram(),
        AmateurBoxingProgram(),
    )
    private val _selectedItem = mutableStateOf(programsList[0])
    private val uiTimer = Timer()
    private val scope = viewModelScope
    val selectedItem: State<BoxingProgram> = _selectedItem
    val isActive = mutableStateOf(false)
    val currentRound = mutableIntStateOf(0)
    val currentWorkTime = mutableStateOf(_selectedItem.value.workDuration)
    val currentRestTime = mutableStateOf(_selectedItem.value.restDuration)
    val currentPreparationTime = mutableStateOf(_selectedItem.value.preparationTime)
    val currentTimerState = mutableStateOf(TimerState.READY_TO_START)
    val progress = mutableFloatStateOf(1f)


    fun onItemSelected(item: BoxingProgram) {
        _selectedItem.value = item
        currentWorkTime.value = item.workDuration
        currentRestTime.value = item.restDuration
        currentPreparationTime.value = item.preparationTime
        currentRound.intValue = 0
        progress.floatValue = 1f
        isActive.value = false
        currentTimerState.value = TimerState.READY_TO_START
    }

    fun startUITimer() {
        isActive.value = !isActive.value
        if (!isActive.value) {
            scope.cancel()
            return
        }
        scope.launch {
            uiTimer.schedule(object : TimerTask() {
                override fun run() {
                    when (currentTimerState.value) {

                        TimerState.READY_TO_START -> {
                            if (isActive.value) {
                                currentTimerState.value = TimerState.PREPARATION
                            }
                        }

                        TimerState.PREPARATION -> {
                            if (!isActive.value) {
                                return
                            }
                            if (currentPreparationTime.value > Duration.ZERO) {
                                currentPreparationTime.value =
                                    currentPreparationTime.value.minus(1000.toDuration(DurationUnit.MILLISECONDS))
                                progress.floatValue =
                                    currentPreparationTime.value.div(_selectedItem.value.preparationTime)
                                        .toFloat()
                            } else {
                                currentTimerState.value = TimerState.WORK
                                currentRound.intValue += 1
                                currentWorkTime.value = _selectedItem.value.workDuration
                            }
                        }

                        TimerState.REST -> {
                            if (!isActive.value) {
                                return
                            }
                            if (currentRestTime.value > Duration.ZERO) {
                                currentRestTime.value =
                                    currentRestTime.value.minus(1000.toDuration(DurationUnit.MILLISECONDS))
                                progress.floatValue =
                                    currentRestTime.value.div(_selectedItem.value.restDuration)
                                        .toFloat()
                            } else {
                                currentTimerState.value = TimerState.WORK
                                currentRestTime.value = _selectedItem.value.restDuration
                                currentRound.intValue += 1
                            }
                        }

                        TimerState.WORK -> {
                            if (!isActive.value) {
                                return
                            }
                            if (currentWorkTime.value > Duration.ZERO) {
                                currentWorkTime.value =
                                    currentWorkTime.value.minus(1000.toDuration(DurationUnit.MILLISECONDS))
                                progress.floatValue =
                                    currentWorkTime.value.div(_selectedItem.value.workDuration)
                                        .toFloat()
                            } else {
                                currentTimerState.value = TimerState.REST
                                currentWorkTime.value = _selectedItem.value.workDuration
                            }

                        }
                    }
                }
            }, 0, 1000)
        }
    }

    fun stopTimer() {
        isActive.value = false
        currentRound.intValue = 1
        currentWorkTime.value = _selectedItem.value.workDuration
        currentRestTime.value = _selectedItem.value.restDuration
        currentPreparationTime.value = _selectedItem.value.preparationTime
        currentTimerState.value = TimerState.READY_TO_START
        progress.floatValue = 1f
    }
}