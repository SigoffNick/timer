package com.example.timer.core.extensions

import kotlin.time.Duration

fun Duration.toFormattedString(): String {
    val totalSeconds = this.inWholeSeconds
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}