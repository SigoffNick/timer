package com.example.timer.core.training_programs

import kotlin.time.Duration.Companion.seconds

class TestingBoxingProgram : BoxingProgram(
    numberOfRounds = 4,
    name = "1",//"Testing Boxing",
    programFlow = listOf(
        10.seconds,
        5.seconds,
        3.seconds,
        5.seconds,
        3.seconds,
        5.seconds,
        3.seconds,
        5.seconds,
    )
)