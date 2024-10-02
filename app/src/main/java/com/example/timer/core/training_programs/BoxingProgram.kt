package com.example.timer.core.training_programs

import kotlin.time.Duration

abstract class BoxingProgram(
    val workDuration: Duration,
    val restDuration: Duration,
    val numberOfRounds: Int,
    val name: String,
    val preparationTime: Duration,
)