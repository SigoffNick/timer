package com.example.timer.service

import com.example.timer.core.enums.StopwatchState
import com.example.timer.core.training_programs.TrainingProgram

data class StopwatchStateData (
    val currentState: StopwatchState,
    val selectedProgram: TrainingProgram,
    val minutes: String,
    val seconds: String
)