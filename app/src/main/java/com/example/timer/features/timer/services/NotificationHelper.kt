package com.example.timer.features.timer.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.timer.core.Constant
import com.example.timer.core.formatTime
import javax.inject.Inject

/**
 * The NotificationHelper class is used to create and manage notifications.
 */

class NotificationHelper @Inject constructor(
    /**
     * The notificationManager variable is used to manage notifications.
     */
    private val notificationManager: NotificationManager,
    /**
     * The notificationBuilder variable is used to build notifications.
     */
    private val notificationBuilder: NotificationCompat.Builder
) {
    /**
     * The createNotificationChannel function is used to create a notification channel.
     * This function is only called on devices running Android Oreo (API 26) or higher.
     * The notification channel is used to group notifications together and provide a way to manage them.
     */
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

    /**
     * The buildNotification function is used to build the notification.
     * The notification is built using the notificationBuilder instance.
     */
    fun buildNotification(): Notification {
        return notificationBuilder.build()
    }

    /**
     * The startForeground function is used to start the service in the foreground.
     * This function is used to display the notification to the user.
     */
    fun cancelNotification() {
        notificationManager.cancel(Constant.NOTIFICATION_ID)
    }

    /**
     * The updateNotification function is used to update the notification's content.
     * The function takes the minutes and seconds as input and updates the notification's content title.
     */
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

    /**
     * The setNotificationColor function is used to update the notification's color.
     */
    fun setNotificationColor(color: Int) {
        notificationBuilder.color = color
        notificationManager.notify(Constant.NOTIFICATION_ID, notificationBuilder.build())
    }
}