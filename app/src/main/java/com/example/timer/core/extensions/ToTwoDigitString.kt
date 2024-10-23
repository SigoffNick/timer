package com.example.timer.core.extensions

fun Long.toTwoDigitString(): String {
    return String.format("%02d", this)
}