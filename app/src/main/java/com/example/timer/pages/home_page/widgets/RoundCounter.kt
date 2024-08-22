package com.example.timer.pages.home_page.widgets

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.timer.pages.home_page.view_models.TimerState

@Composable
fun RoundCounter(totalRounds: Int, currentRound: Int) {
    Text(
        text = "Раунд $currentRound/$totalRounds",
        color = Color.White,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold
    )
}

@Preview()
@Composable
fun RoundCounterPreview() {
    RoundCounter(
        totalRounds = 5,
        currentRound = 3
    )
}