package com.example.timer.pages.home_page

import PlayButton
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.timer.pages.home_page.view_models.HomeViewModel
import com.example.timer.pages.home_page.widgets.HomeScreen
import com.example.timer.ui.theme.TimerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel = HomeViewModel()
        setContent {
            TimerTheme {
                HomeScreen(viewModel)
            }
        }
    }
}



