package com.example.timer.core.training_programs

import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class ClassicBoxingProgram : BoxingProgram(
    numberOfRounds = 12,
    name = "Classic Boxing",
    programFlow = listOf(
        10.seconds,
        3.minutes,
        1.minutes,
        3.minutes,
        1.minutes,
        3.minutes,
        1.minutes,
        3.minutes,
        1.minutes,
        3.minutes,
        1.minutes,
        3.minutes,
        1.minutes,
        3.minutes,
        1.minutes,
        3.minutes,
        1.minutes,
        3.minutes,
        1.minutes,
        3.minutes,
        1.minutes,
        3.minutes,
        1.minutes,
        3.minutes,
    )
)