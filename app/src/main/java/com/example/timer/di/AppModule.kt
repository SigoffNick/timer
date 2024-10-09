package com.example.timer.di

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.timer.core.Constant
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideNotificationManager(context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Provides
    fun provideNotificationBuilder(context: Context): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, Constant.NOTIFICATION_CHANNEL_ID)
    }
}