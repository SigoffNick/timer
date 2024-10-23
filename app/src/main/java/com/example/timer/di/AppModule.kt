// AppModule.kt
package com.example.timer.di

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.timer.R
import com.example.timer.core.Constant
import com.example.timer.features.timer.services.NotificationHelper
import com.example.timer.features.timer.services.ServiceHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object AppModule {

    @ServiceScoped
    @Provides
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @ServiceScoped
    @Provides
    fun provideNotificationBuilder(@ApplicationContext context: Context): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, Constant.NOTIFICATION_CHANNEL_ID)
            .setContentTitle("00:00:00")
            .setSmallIcon(R.drawable.baseline_alarm_on_24)
            .setOngoing(true)
            .addAction(0, "Cancel", ServiceHelper.cancelPendingIntent(context))
            .addAction(0, "Stop", ServiceHelper.stopPendingIntent(context))
            .setContentIntent(ServiceHelper.clickPendingIntent(context))
            .setColorized(true)
    }

    @ServiceScoped
    @Provides
    fun provideNotificationHelper(
        notificationManager: NotificationManager,
        notificationBuilder: NotificationCompat.Builder
    ): NotificationHelper {
        return NotificationHelper(notificationManager, notificationBuilder)
    }
}