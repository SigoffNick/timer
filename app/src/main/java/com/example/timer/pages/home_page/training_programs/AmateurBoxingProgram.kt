package com.example.timer.pages.home_page.training_programs

import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class AmateurBoxingProgram : BoxingProgram(
    workDuration = 2.minutes,
    restDuration = 1.minutes,
    numberOfRounds = 4,
    name = "Amateur Boxing",
    preparationTime = 20.seconds
)