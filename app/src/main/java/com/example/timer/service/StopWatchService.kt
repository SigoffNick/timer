package com.example.timer.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import androidx.compose.runtime.mutableStateOf
import com.example.timer.core.Constant
import com.example.timer.core.enums.ServiceAction
import com.example.timer.core.enums.StopwatchState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
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

    /**
     * The NotificationHelper class is used to create and manage notifications.
     */
    @Inject
    lateinit var notificationHelper: NotificationHelper

    /**
     * The _selectedProgram variable is used to store the selected training program.
     */
    val selectedProgram = mutableStateOf(Constant.PROGRAMS_LIST[0])

    /**
     * The currentState variable is used to store the current state of the stopwatch.
     */
    var currentState = mutableStateOf(StopwatchState.Idle)

    /**
     * The StopwatchBinder class is used to bind the StopwatchService to the MainActivity.
     */
    private val binder = StopwatchBinder()

    /**
     * The duration variable is used to store the current duration of the stopwatch.
     */
    var duration = mutableStateOf(selectedProgram.value.programFlow[0].duration)

    /**
     * The timer variable is used to create a Timer object that will be used to update the stopwatch every second.
     */
    private lateinit var timer: Timer

    val currentStep = mutableStateOf(selectedProgram.value.programFlow[0])

    private var currentStepIndex: Int = 0


    /**
     * The onBind method is used to bind the StopwatchService to the MainActivity.
     */
    override fun onBind(intent: Intent?) = binder

    /**
     * The part of the StopwatchService that is designed to catch intents is the onStartCommand method.
     * This method handles the incoming intents and performs actions based on the intent's action or extra data.
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val stopwatchState = intent?.getStringExtra(STOPWATCH_STATE)
        if (stopwatchState != null) {
            handleStopwatchState(stopwatchState)
        }

        val programIndex = intent?.getStringExtra(ServiceHelper.SET_PROGRAM)
        if (programIndex != null) {
            handleProgramIndex(programIndex)
        }

        val action = intent?.action
        if (action != null) {
            handleAction(action)
        }

        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * The handleStopwatchState method is used to handle the stopwatch state based on the given state.
     */
    private fun handleStopwatchState(stopwatchState: String) {
        when (stopwatchState) {
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
    }

    private fun handleProgramIndex(index: String) {
        selectedProgram.value = Constant.PROGRAMS_LIST[index.toInt()]
        cancelStopwatch()
    }

    /**
     * The handleAction method is used to handle the action based on the given action.
     */
    private fun handleAction(action: String) {
        when (action) {
            ServiceAction.ACTION_SERVICE_START_PROGRAM.name -> {
                startForegroundService()
                startStopwatch { minutes, seconds ->
                    notificationHelper.updateNotification(minutes = minutes, seconds = seconds)
                }
                notificationHelper.setStopButton(this)
            }

            ServiceAction.ACTION_SERVICE_STOP_PROGRAM.name -> {
                stopStopwatch()
                notificationHelper.setResumeButton(this)
            }

            ServiceAction.ACTION_SERVICE_CANCEL_PROGRAM.name -> {
                stopForegroundService()
                cancelStopwatch()
            }

            ServiceAction.ACTION_SERVICE_SET_PROGRAM.name -> {
                cancelStopwatch()
            }
        }
    }

    /**
     * The startStopwatch method is used to start the stopwatch.
     */
    // TODO Can we don't use global scope
    @OptIn(DelicateCoroutinesApi::class)
    private fun startStopwatch(onTick: (m: String, s: String) -> Unit) {
        currentState.value = StopwatchState.Started
        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            if (duration.value == Duration.ZERO) {
                currentStepIndex++
                if (currentStepIndex == selectedProgram.value.programFlow.size) {
                    cancelStopwatch()
                    return@fixedRateTimer
                }
                GlobalScope.launch {
                    delay(1000L)
                    duration.value = selectedProgram.value.programFlow[currentStepIndex].duration
                    currentStep.value = selectedProgram.value.programFlow[currentStepIndex]
                }
            } else {
                duration.value = duration.value.minus(1.seconds)
                duration.value.toComponents { minutes, seconds, _ ->
                    onTick(minutes.toString(), seconds.toString())
                }
            }
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
        if (this::timer.isInitialized) {
            timer.cancel()
        }
        currentState.value = StopwatchState.Idle
        currentStepIndex = 0
        currentStep.value = selectedProgram.value.programFlow[currentStepIndex]
        resetTimer()
    }

    private fun resetTimer() {
        duration.value = selectedProgram.value.programFlow[0].duration
    }

    /**
     * The StopwatchBinder class is used to bind the StopwatchService to the MainActivity.
     */
    inner class StopwatchBinder : Binder() {
        fun getService(): StopwatchService = this@StopwatchService
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

    companion object {
        const val STOPWATCH_STATE = "STOPWATCH_STATE"
    }
}
