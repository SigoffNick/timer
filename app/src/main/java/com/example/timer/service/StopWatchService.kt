package com.example.timer.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import com.example.timer.core.Constant
import com.example.timer.core.formatTime
import com.example.timer.core.pad
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * The StopwatchService is declared in the AndroidManifest.xml file to handle specific intents.
 * This StopwatchService will catch and handle the intents triggered by the triggerForegroundService function.
 */
@AndroidEntryPoint
class StopwatchService : Service() {

    @Inject
    lateinit var notificationHelper: NotificationHelper

    private val binder = StopwatchBinder()

    private var duration: Duration = Duration.ZERO
    private lateinit var timer: Timer

    var minutes = mutableStateOf("00")
        private set
    var seconds = mutableStateOf("00")
        private set
    var currentState = mutableStateOf(StopwatchState.Idle)
        private set

    override fun onBind(intent: Intent?) = binder

    /**
     * The part of the StopwatchService that is designed to catch intents is the onStartCommand method.
     * This method handles the incoming intents and performs actions based on the intent's action or extra data.
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Check the intent's extra data to determine the action to be performed.
        when (intent?.getStringExtra(Constant.STOPWATCH_STATE)) {
            StopwatchState.Started.name -> {
                notificationHelper.setStopButton(this)
                startForegroundService()
                startStopwatch { minutes, seconds ->
                    notificationHelper.updateNotification(minutes = minutes, seconds = seconds)
                }
            }

            StopwatchState.Stopped.name -> {
                notificationHelper.setResumeButton(this)
                stopStopwatch()
            }

            StopwatchState.Canceled.name -> {
                stopForegroundService()
                stopStopwatch()
                cancelStopwatch()
            }
        }

        val initialTime = intent?.getStringExtra(Constant.INITIAL_TIME)
        if (initialTime != null) {
            val (m, s) = initialTime.split(":")
            duration = createDuration(m.toInt(), s.toInt())
        }

        intent?.action.let {
            when (it) {
                Constant.ACTION_SERVICE_START -> {
                    startForegroundService()
                    startStopwatch { minutes, seconds ->
                        notificationHelper.updateNotification(minutes = minutes, seconds = seconds)
                    }
                    notificationHelper.setStopButton(this)
                }

                Constant.ACTION_SERVICE_STOP -> {
                    stopStopwatch()
                    notificationHelper.setResumeButton(this)
                }

                Constant.ACTION_SERVICE_CANCEL -> {
                    stopForegroundService()
                    stopStopwatch()
                    cancelStopwatch()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startStopwatch(onTick: (m: String, s: String) -> Unit) {
        currentState.value = StopwatchState.Started
        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            duration = duration.minus(1.seconds)
            updateTimeUnits()
            onTick(minutes.value, seconds.value)
        }
    }

    private fun stopStopwatch() {
        if (this::timer.isInitialized) {
            timer.cancel()
        }
        currentState.value = StopwatchState.Stopped
    }

    private fun cancelStopwatch() {
        duration = Duration.ZERO
        currentState.value = StopwatchState.Idle
        updateTimeUnits()
    }

    private fun updateTimeUnits() {
        duration.toComponents { minutes, seconds, _ ->
            this.minutes.value = minutes.toInt().pad()
            this.seconds.value = seconds.pad()
        }
    }


    inner class StopwatchBinder : Binder() {
        fun getService(): StopwatchService = this@StopwatchService
    }

    private fun createDuration(minutes: Int, seconds: Int): Duration {
        val totalSeconds = minutes * 60 + seconds
        return totalSeconds.seconds
    }

    private fun startForegroundService() {
        notificationHelper.createNotificationChannel()
        startForeground(Constant.NOTIFICATION_ID, notificationHelper.buildNotification())
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun stopForegroundService() {
        GlobalScope.launch(Dispatchers.Default) {
            notificationHelper.cancelNotification()
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        }
    }
}

enum class StopwatchState {
    Idle,
    Started,
    Stopped,
    Canceled
}