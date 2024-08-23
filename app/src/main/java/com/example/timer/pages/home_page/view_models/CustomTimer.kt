package com.example.timer.pages.home_page.view_models

import android.os.CountDownTimer
import java.util.Timer
import java.util.TimerTask

class CustomTimer(private val totalTime: Long, private val interval: Long, private val onTickAction: (Long) -> Unit, private val onFinishAction: () -> Unit) {
    private var countDownTimer: CountDownTimer? = null
    private var timeRemaining: Long = totalTime
    val timer: Timer = Timer()

    fun start() {
        countDownTimer = object : CountDownTimer(timeRemaining, interval) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                onTickAction(millisUntilFinished)
            }

            override fun onFinish() {
                timeRemaining = 0
                onFinishAction()
            }
        }.start()
    }

    fun pause() {
        countDownTimer?.cancel()
    }

    fun stop() {
        countDownTimer?.cancel()
        timeRemaining = totalTime
    }
}