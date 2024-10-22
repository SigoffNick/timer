package com.example.timer.core.training_programs

import com.example.timer.core.training_programs.program_step.ProgramStep

abstract class TrainingProgram(
    val name: String,
    val programFlow: List<ProgramStep>,
)