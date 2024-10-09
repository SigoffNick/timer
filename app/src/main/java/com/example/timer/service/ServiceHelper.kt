package com.example.timer.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.timer.core.Constant
import com.example.timer.MainActivity


object ServiceHelper {

    private const val FLAG = PendingIntent.FLAG_IMMUTABLE

    fun clickPendingIntent(context: Context): PendingIntent {
        val clickIntent = Intent(context, MainActivity::class.java).apply {
            putExtra(Constant.STOPWATCH_STATE, StopwatchState.Started.name)
        }
        return PendingIntent.getActivity(
            context, Constant.CLICK_REQUEST_CODE, clickIntent, FLAG
        )
    }

    fun stopPendingIntent(context: Context): PendingIntent {
        val stopIntent = Intent(context, StopwatchService::class.java).apply {
            putExtra(Constant.STOPWATCH_STATE, StopwatchState.Stopped.name)
        }
        return PendingIntent.getService(
            context, Constant.STOP_REQUEST_CODE, stopIntent, FLAG
        )
    }

    fun resumePendingIntent(context: Context): PendingIntent {
        val resumeIntent = Intent(context, StopwatchService::class.java).apply {
            putExtra(Constant.STOPWATCH_STATE, StopwatchState.Started.name)
        }
        return PendingIntent.getService(
            context, Constant.RESUME_REQUEST_CODE, resumeIntent, FLAG
        )
    }

    fun cancelPendingIntent(context: Context): PendingIntent {
        val cancelIntent = Intent(context, StopwatchService::class.java).apply {
            putExtra(Constant.STOPWATCH_STATE, StopwatchState.Canceled.name)
        }
        return PendingIntent.getService(
            context, Constant.CANCEL_REQUEST_CODE, cancelIntent, FLAG
        )
    }

    /**
    Evoked from HomePageViewModel.kt to trigger the StopwatchService with the given action
    Intents are delivered to the onStartCommand function when the startService method is called with an Intent object.
    Here is a step-by-step explanation:
    1. Intent Creation: An Intent object is created, specifying the target service (in this case, StopwatchService).
    2. Setting Action: The action or additional data is set on the Intent object.
    3. Starting the Service: The startService method is called with the Intent object. This method sends the Intent to the service.
    4. Service Handling: The Android system delivers the Intent to the onStartCommand method of the service.
    @Param context: The context from which the service is being started.
    @Param action: A string representing the action to be performed by the service
     */
    fun triggerForegroundService(context: Context, action: String, minutes: Int = 0, seconds: Int = 0) {
        // An Intent is created with the context and the StopwatchService class. This intent is used to specify the service to be started.
        // The startService method of the context is called with the intent, which starts the StopwatchService.
        Intent(context, StopwatchService::class.java).apply {
            this.action = action
            putExtra(Constant.INITIAL_TIME, "$minutes:$seconds")
            context.startService(this)
        }
    }
}