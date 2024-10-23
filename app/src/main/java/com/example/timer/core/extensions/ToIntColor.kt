package com.example.timer.core.extensions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun Color.toIntColor(): Int {
    return this.toArgb()
}