package com.example.timer.pages.home_page.widgets

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun RoundCounter(totalRounds: Int, currentRound: Int) {
    Text(
        text = "Раунд $currentRound из $totalRounds",
        color = Color.White,
        fontSize = 24.sp,
    )
}