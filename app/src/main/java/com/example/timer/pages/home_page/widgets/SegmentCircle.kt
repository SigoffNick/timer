package com.example.timer.pages.home_page.widgets

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun SegmentedCircle(
    totalRounds: Int,
    currentRound: Int,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    strokeWidth: Float
) {
    Canvas(modifier = modifier) {
        val segmentAngle = 360f / totalRounds
        for (i in 0 until totalRounds) {
            drawArc(
                color = when {
                    (i < currentRound - 1) -> color.copy(alpha = 0.3f)
                    i == currentRound - 1 -> color
                    else -> Color.White
                },
                startAngle = i * segmentAngle - 90f,
                sweepAngle = segmentAngle - 2,
                useCenter = false,
                style = Stroke(width = strokeWidth)
            )
        }
    }
}