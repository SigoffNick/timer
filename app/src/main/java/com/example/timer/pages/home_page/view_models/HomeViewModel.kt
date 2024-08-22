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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
    val selectedItem: State<BoxingProgram> = _selectedItem
    val isPlaying = mutableStateOf(false)
    val currentRound = mutableIntStateOf(1)
    val currentWorkTime = mutableStateOf(_selectedItem.value.workDuration)
    val currentRestTime = mutableStateOf(_selectedItem.value.restDuration)
    val timerState = mutableStateOf(TimerState.READY_TO_START)
    val progress = mutableFloatStateOf(1f)


    fun onItemSelected(item: BoxingProgram) {
        _selectedItem.value = item
        currentWorkTime.value = item.workDuration
        currentRestTime.value = item.restDuration
        currentRound.intValue = 1
        progress.floatValue = 1f
        isPlaying.value = false
        timerState.value = TimerState.READY_TO_START
    }

    fun switchTimer() {
        isPlaying.value = !isPlaying.value
        if (isPlaying.value) {
            timerState.value = TimerState.WORK
            viewModelScope.launch {
                while (isPlaying.value) {
                    if (currentRound.intValue > _selectedItem.value.numberOfRounds) {
                        isPlaying.value = false
                        currentRound.intValue = 1
                        currentWorkTime.value = _selectedItem.value.workDuration
                        currentRestTime.value = _selectedItem.value.restDuration
                        timerState.value = TimerState.READY_TO_START
                        progress.floatValue = 1f
                        break
                    }
                    if (currentWorkTime.value < Duration.ZERO) {
                        if (currentRound.intValue == _selectedItem.value.numberOfRounds) {
                            isPlaying.value = false
                            currentRound.intValue = 1
                            currentWorkTime.value = _selectedItem.value.workDuration
                            currentRestTime.value = _selectedItem.value.restDuration
                            timerState.value = TimerState.READY_TO_START
                            progress.floatValue = 1f
                            break
                        }
                        if (timerState.value != TimerState.REST) {
                            progress.floatValue = 1f
                        }
                        timerState.value = TimerState.REST
                        if (currentRestTime.value < Duration.ZERO) {
                            timerState.value = TimerState.WORK
                            currentRound.intValue += 1
                            currentWorkTime.value = _selectedItem.value.workDuration
                            currentRestTime.value = _selectedItem.value.restDuration
                            progress.floatValue = 1f
                        } else {
                            delay(1000L)
                            currentRestTime.value =
                                currentRestTime.value.minus(1.toDuration(DurationUnit.SECONDS))
                            progress.floatValue =
                                currentRestTime.value.div(_selectedItem.value.restDuration)
                                    .toFloat()
                        }
                    } else {
                        delay(1000L)
                        currentWorkTime.value =
                            currentWorkTime.value.minus(1.toDuration(DurationUnit.SECONDS))
                        progress.floatValue =
                            currentWorkTime.value.div(_selectedItem.value.workDuration).toFloat()
                    }
                }
            }
        }
    }

    fun stopTimer() {
        isPlaying.value = false
        currentRound.intValue = 1
        currentWorkTime.value = _selectedItem.value.workDuration
        currentRestTime.value = _selectedItem.value.restDuration
        timerState.value = TimerState.READY_TO_START
        progress.floatValue = 1f
    }
}
