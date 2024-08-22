package com.example.timer.pages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.timer.pages.home_page.home_page_experimental_design.view_models.HomePageExperimentalDesignViewModel
import com.example.timer.pages.home_page.home_page_experimental_design.widgets.HomePageExperimentalDesign
import com.example.timer.pages.home_page.view_models.HomePageViewModel
import com.example.timer.pages.home_page.widgets.HomePage
import com.example.timer.ui.theme.TimerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel = HomePageViewModel()
        setContent {
            TimerTheme {
                HomePage(viewModel)
            }
        }
    }
}



