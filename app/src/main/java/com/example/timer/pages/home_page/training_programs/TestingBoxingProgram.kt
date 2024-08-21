package com.example.timer.pages.home_page.training_programs

import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class TestingBoxingProgram : BoxingProgram(
    workDuration = 10.seconds,
    restDuration = 5.seconds,
    numberOfRounds = 12,
    name = "Testing Boxing"
)