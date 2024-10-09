package com.example.timer.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service.STOP_FOREGROUND_REMOVE
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.timer.core.Constant
import com.example.timer.core.formatTime
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotificationHelper {
    @Inject
     lateinit var notificationManager: NotificationManager

    @Inject
     lateinit var notificationBuilder: NotificationCompat.Builder

    fun createNotificationChannel() {
        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                Constant.NOTIFICATION_CHANNEL_ID,
                Constant.NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        notificationManager.createNotificationChannel(channel)
    }

    fun buildNotification(): Notification {
       return  notificationBuilder.build()
    }

    fun cancelNotification() {
        notificationManager.cancel(Constant.NOTIFICATION_ID)
    }

    fun updateNotification(minutes: String, seconds: String) {
        notificationManager.notify(
            Constant.NOTIFICATION_ID,
            notificationBuilder.setContentTitle(
                formatTime(
                    minutes = minutes,
                    seconds = seconds,
                )
            ).build()
        )
    }

    /**
     * The setStopButton is used to update the notification's action buttons.
     */
    @SuppressLint("RestrictedApi")
    fun setStopButton(context: Context) {
        notificationBuilder.mActions.removeAt(1)
        notificationBuilder.mActions.add(
            1,
            NotificationCompat.Action(
                0,
                "Stop",
                ServiceHelper.stopPendingIntent(context)
            )
        )
        notificationManager.notify(Constant.NOTIFICATION_ID, notificationBuilder.build())
    }

    /**
     * The setResumeButton is used to update the notification's action buttons.
     */
    @SuppressLint("RestrictedApi")
    fun setResumeButton(context: Context) {
        notificationBuilder.mActions.removeAt(1)
        notificationBuilder.mActions.add(
            1,
            NotificationCompat.Action(
                0,
                "Resume",
                ServiceHelper.resumePendingIntent(context)
            )
        )
        notificationManager.notify(Constant.NOTIFICATION_ID, notificationBuilder.build())
    }

}