package com.example.timer.core.training_programs

import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class ClassicBoxingProgram : BoxingProgram(
    workDuration = 3.minutes,
    restDuration = 1.minutes,
    numberOfRounds = 12,
    name = "Classic Boxing",
    preparationTime = 30.seconds
)