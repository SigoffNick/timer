package com.example.timer.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import androidx.compose.runtime.mutableStateOf
import com.example.timer.core.Constant
import com.example.timer.core.enums.StopwatchState
import com.example.timer.core.pad
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * The StopwatchService is declared in the AndroidManifest.xml file to handle specific intents.
 * This StopwatchService will catch and handle the intents triggered by the triggerForegroundService function.
 */
@AndroidEntryPoint
class StopwatchService : Service() {

    /**
     * The NotificationHelper class is used to create and manage notifications.
     */
    private val notificationHelper: NotificationHelper = NotificationHelper()

    /**
     * The StopwatchBinder class is used to bind the StopwatchService to the MainActivity.
     */
    private val binder = StopwatchBinder()

    /**
     * The duration variable is used to store the current duration of the stopwatch.
     */
    private var duration: Duration = Duration.ZERO

    /**
     * The timer variable is used to create a Timer object that will be used to update the stopwatch every second.
     */
    private lateinit var timer: Timer

    /**
     * The minutes and seconds variables are used to store the current minutes and seconds of the stopwatch.
     */
    var minutes = mutableStateOf("00")
        private set

    /**
     * The seconds variable is used to store the current seconds of the stopwatch.
     */
    var seconds = mutableStateOf("00")
        private set

    /**
     * The currentState variable is used to store the current state of the stopwatch.
     */
    private var currentState = mutableStateOf(StopwatchState.Idle)

    /**
     * The onBind method is used to bind the StopwatchService to the MainActivity.
     */
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

    /**
     * The startStopwatch method is used to start the stopwatch.
     */
    private fun startStopwatch(onTick: (m: String, s: String) -> Unit) {
        currentState.value = StopwatchState.Started
        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            duration = duration.minus(1.seconds)
            updateTimeUnits()
            onTick(minutes.value, seconds.value)
        }
    }

    /**
     * The stopStopwatch method is used to stop the stopwatch.
     */
    private fun stopStopwatch() {
        if (this::timer.isInitialized) {
            timer.cancel()
        }
        currentState.value = StopwatchState.Stopped
    }

    /**
     * The cancelStopwatch method is used to cancel the stopwatch.
     */
    private fun cancelStopwatch() {
        duration = Duration.ZERO
        currentState.value = StopwatchState.Idle
        updateTimeUnits()
    }

    /**
     * The updateTimeUnits method is used to update the minutes and seconds variables based on the current duration.
     */
    private fun updateTimeUnits() {
        duration.toComponents { minutes, seconds, _ ->
            this.minutes.value = minutes.toInt().pad()
            this.seconds.value = seconds.pad()
        }
    }

    /**
     * The StopwatchBinder class is used to bind the StopwatchService to the MainActivity.
     */
    inner class StopwatchBinder : Binder() {
        fun getService(): StopwatchService = this@StopwatchService
    }

    /**
     * The createDuration method is used to create a Duration object from the given minutes and seconds.
     */
    private fun createDuration(minutes: Int, seconds: Int): Duration {
        val totalSeconds = minutes * 60 + seconds
        return totalSeconds.seconds
    }

    /**
     * The startForegroundService method is used to start the service in the foreground.
     */
    private fun startForegroundService() {
        notificationHelper.createNotificationChannel()
        startForeground(Constant.NOTIFICATION_ID, notificationHelper.buildNotification())
    }

    /**
     * The stopForegroundService method is used to stop the service in the foreground.
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun stopForegroundService() {
        GlobalScope.launch(Dispatchers.Default) {
            notificationHelper.cancelNotification()
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        }
    }
}
