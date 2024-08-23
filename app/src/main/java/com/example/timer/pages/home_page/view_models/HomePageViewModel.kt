package com.example.timer.pages.home_page.view_models

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import com.example.timer.pages.home_page.training_programs.AmateurBoxingProgram
import com.example.timer.pages.home_page.training_programs.BoxingProgram
import com.example.timer.pages.home_page.training_programs.ClassicBoxingProgram
import com.example.timer.pages.home_page.training_programs.TestingBoxingProgram
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class HomePageViewModel : ViewModel() {
    val programsList = listOf(
        TestingBoxingProgram(),
        ClassicBoxingProgram(),
        AmateurBoxingProgram(),
    )
    private val _selectedItem = mutableStateOf(programsList[0])
    private var uiTimer: CustomTimer? = null
    val selectedItem: State<BoxingProgram> = _selectedItem
    val isActive = mutableStateOf(false)
    val currentRound = mutableIntStateOf(0)
    val currentTimerState = mutableStateOf(TimerState.READY_TO_START)
    val shownTime = mutableStateOf(_selectedItem.value.workDuration)


    fun onItemSelected(item: BoxingProgram) {
        _selectedItem.value = item
        currentRound.intValue = 0
        isActive.value = false
        currentTimerState.value = TimerState.READY_TO_START
    }

    fun startUITimer() {
        isActive.value = !isActive.value
        if (isActive.value) {
            if (currentTimerState.value == TimerState.READY_TO_START) {
                startTimer()
            } else {
                uiTimer?.start()
            }
        } else {
            pauseTimer()
        }
    }

    private fun pauseTimer() {
        uiTimer?.pause()
    }

    private fun startTimer() {
        when (currentTimerState.value) {
            TimerState.READY_TO_START -> {
                currentTimerState.value = TimerState.PREPARATION
                startTimer()
            }

            TimerState.PREPARATION -> {
                uiTimer = CustomTimer(
                    _selectedItem.value.preparationTime.inWholeMilliseconds,
                    1000,
                    onTickAction = { millisUntilFinish ->
                        shownTime.value = millisUntilFinish.toDuration(DurationUnit.MILLISECONDS)
                    },
                    onFinishAction = {
                        currentTimerState.value = TimerState.WORK
                        startTimer()
                    }
                )
                uiTimer?.start()
            }

            TimerState.REST -> {
                uiTimer = CustomTimer(
                    _selectedItem.value.restDuration.inWholeMilliseconds,
                    1000,
                    onTickAction = { millisUntilFinish ->
                        shownTime.value = millisUntilFinish.toDuration(DurationUnit.MILLISECONDS)
                    },
                    onFinishAction = {
                        currentTimerState.value = TimerState.WORK
                        startTimer()
                    }
                )
                uiTimer?.start()
            }

            TimerState.WORK -> {
                currentRound.value += 1
                uiTimer = CustomTimer(
                    _selectedItem.value.workDuration.inWholeMilliseconds,
                    1000,
                    onTickAction = { millisUntilFinish ->
                        shownTime.value = millisUntilFinish.toDuration(DurationUnit.MILLISECONDS)
                    },
                    onFinishAction = {
                        if (currentRound.intValue == _selectedItem.value.numberOfRounds) {
                            currentTimerState.value = TimerState.READY_TO_START
                            isActive.value = false
                        } else {
                            currentTimerState.value = TimerState.REST
                            startTimer()
                        }
                    })
                uiTimer?.start()
            }
        }
    }

    fun stopTimer() {
        isActive.value = false
        currentRound.intValue = 0
        currentTimerState.value = TimerState.READY_TO_START
    }
}
