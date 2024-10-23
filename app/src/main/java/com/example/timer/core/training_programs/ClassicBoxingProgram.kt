package com.example.timer.core.training_programs

import com.example.timer.core.training_programs.classic_boxing.ClassicBoxingRestStep
import com.example.timer.core.training_programs.classic_boxing.ClassicBoxingWorkStep
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class ClassicBoxingProgram : TrainingProgram(
    name = "Classic Boxing",
    programFlow = listOf(
        ClassicPreparationStep(),
        ClassicBoxingWorkStep(),
        ClassicBoxingRestStep(),
        ClassicBoxingWorkStep(),
        ClassicBoxingRestStep(),
        ClassicBoxingWorkStep(),
        ClassicBoxingRestStep(),
        ClassicBoxingWorkStep(),
        ClassicBoxingRestStep(),
        ClassicBoxingWorkStep(),
        ClassicBoxingRestStep(),
        ClassicBoxingWorkStep(),
        ClassicBoxingRestStep(),
        ClassicBoxingWorkStep(),
        ClassicBoxingRestStep(),
        ClassicBoxingWorkStep(),
        ClassicBoxingRestStep(),
        ClassicBoxingWorkStep(),
        ClassicBoxingRestStep(),
        ClassicBoxingWorkStep(),
        ClassicBoxingRestStep(),
        ClassicBoxingWorkStep(),
        ClassicBoxingRestStep(),
        ClassicBoxingWorkStep(),
    )
)