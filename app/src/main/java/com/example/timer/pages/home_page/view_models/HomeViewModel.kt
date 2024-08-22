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

enum class TimerState {
    WORK,
    REST,
    READY_TO_START
}

class HomeViewModel : ViewModel() {
    val programsList = listOf(
        TestingBoxingProgram(),
        ClassicBoxingProgram(),
        AmateurBoxingProgram(),
    )
    private val _selectedItem = mutableStateOf(programsList[0])
    private val uiTimer = Timer()
    val selectedItem: State<BoxingProgram> = _selectedItem
    val isPlaying = mutableStateOf(false)
    val currentRound = mutableIntStateOf(1)
    val currentWorkTime = mutableStateOf(_selectedItem.value.workDuration)
    val currentRestTime = mutableStateOf(_selectedItem.value.restDuration)
    val currentTimerState = mutableStateOf(TimerState.READY_TO_START)
    val progress = mutableFloatStateOf(1f)
    val coro = viewModelScope


    fun onItemSelected(item: BoxingProgram) {
        _selectedItem.value = item
        currentWorkTime.value = item.workDuration
        currentRestTime.value = item.restDuration
        currentRound.intValue = 1
        progress.floatValue = 1f
        isPlaying.value = false
        currentTimerState.value = TimerState.READY_TO_START
    }

    fun startUITimer() {
        isPlaying.value = !isPlaying.value
        if (!isPlaying.value) {
            coro.cancel()
            return
        }
        coro.launch {
            uiTimer.schedule(object : TimerTask() {
                override fun run() {
                    when (currentTimerState.value) {

                        TimerState.READY_TO_START -> {
                            if (isPlaying.value) {
                                currentTimerState.value = TimerState.WORK
                            }
                        }

                        TimerState.REST -> {
                            if (!isPlaying.value) {
                                return
                            }
                            if (currentRestTime.value > Duration.ZERO) {
                                currentRestTime.value =
                                    currentRestTime.value.minus(16.toDuration(DurationUnit.MILLISECONDS))
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
                            if (!isPlaying.value) {
                                return
                            }
                            if (currentWorkTime.value > Duration.ZERO) {
                                currentWorkTime.value =
                                    currentWorkTime.value.minus(16.toDuration(DurationUnit.MILLISECONDS))
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
            }, 0, 16)
        }
    }

    fun stopTimer() {
        isPlaying.value = false
        currentRound.intValue = 1
        currentWorkTime.value = _selectedItem.value.workDuration
        currentRestTime.value = _selectedItem.value.restDuration
        currentTimerState.value = TimerState.READY_TO_START
        progress.floatValue = 1f
    }
}
