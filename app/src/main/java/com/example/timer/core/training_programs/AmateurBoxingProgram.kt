package com.example.timer.core.training_programs

import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class AmateurBoxingProgram : BoxingProgram(
    numberOfRounds = 4,
    name = "3",//"Amateur Boxing",
    programFlow = listOf(
        10.seconds,
        2.minutes,
        1.minutes,
        2.minutes,
        1.minutes,
        2.minutes,
        1.minutes,
        2.minutes,
    )
)