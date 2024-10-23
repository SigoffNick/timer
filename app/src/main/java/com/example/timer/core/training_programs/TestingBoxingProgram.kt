package com.example.timer.core.training_programs

import com.example.timer.core.training_programs.program_step.RestStep
import com.example.timer.core.training_programs.program_step.WorkStep
import kotlin.time.Duration.Companion.seconds

class TestingBoxingProgram : TrainingProgram(
    name = "Testing Boxing",
    programFlow = listOf(
        ClassicPreparationStep(),
        WorkStep(duration = 10.seconds),
        RestStep(duration = 4.seconds),
        WorkStep(duration = 10.seconds),
    )
)