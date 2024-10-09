package com.example.timer.core.training_programs

import kotlin.time.Duration

abstract class BoxingProgram(
    val numberOfRounds: Int,
    val name: String,
    val programFlow: List<Duration>,
)