package com.example.timer.core

import com.example.timer.core.training_programs.AmateurBoxingProgram
import com.example.timer.core.training_programs.ClassicBoxingProgram
import com.example.timer.core.training_programs.TestingBoxingProgram

object Constant {

    const val NOTIFICATION_CHANNEL_ID = "STOPWATCH_NOTIFICATION_ID"
    const val NOTIFICATION_CHANNEL_NAME = "STOPWATCH_NOTIFICATION"
    const val NOTIFICATION_ID = 10

    const val CLICK_REQUEST_CODE = 100
    const val CANCEL_REQUEST_CODE = 101
    const val STOP_REQUEST_CODE = 102
    const val RESUME_REQUEST_CODE = 103

    /**
     * The programsList variable is used to store the list of available training programs.
     */
    val PROGRAMS_LIST = listOf(
        TestingBoxingProgram(),
        ClassicBoxingProgram(),
        AmateurBoxingProgram(),
    )
}