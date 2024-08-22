package com.example.timer.pages.home_page

import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.timer.pages.home_page.view_models.HomeViewModel
import com.example.timer.pages.home_page.widgets.HomeScreen
import com.example.timer.ui.theme.TimerTheme

class MainActivity : ComponentActivity() {
    private lateinit var wakeLock: PowerManager.WakeLock

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "MyApp::MyWakelockTag")
        wakeLock.acquire(10*60*1000L /*10 minutes*/)
        enableEdgeToEdge()
        val viewModel = HomeViewModel()
        setContent {
            TimerTheme {
                HomeScreen(viewModel)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (wakeLock.isHeld) {
            wakeLock.release()
        }
    }
}



