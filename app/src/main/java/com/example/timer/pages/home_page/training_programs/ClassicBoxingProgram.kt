package com.example.timer.pages.home_page.training_programs

import kotlin.time.Duration.Companion.minutes

class ClassicBoxingProgram : BoxingProgram(
    workDuration = 3.minutes,
    restDuration = 1.minutes,
    numberOfRounds = 12,
    name = "Classic Boxing"
)