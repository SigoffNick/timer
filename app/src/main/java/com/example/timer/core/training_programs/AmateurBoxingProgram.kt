package com.example.timer.core.training_programs

import com.example.timer.core.training_programs.amateur_boxing.AmateurBoxingWorkStep
import com.example.timer.core.training_programs.classic_boxing.ClassicBoxingRestStep
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class AmateurBoxingProgram : TrainingProgram(
    name = "3",//"Amateur Boxing",
    programFlow = listOf(
        ClassicPreparationStep(),
        AmateurBoxingWorkStep(),
        ClassicBoxingRestStep(),
        AmateurBoxingWorkStep(),
        ClassicBoxingRestStep(),
        AmateurBoxingWorkStep(),
        ClassicBoxingRestStep(),
    )
)