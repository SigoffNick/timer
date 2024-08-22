package com.example.timer.pages.home_page.widgets
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import kotlin.time.Duration

@Composable
fun MainTimer(
    time: Duration,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val maxFontSize = min(maxWidth, maxHeight) / 3

        Text(
            text = time.toMinutesSeconds(),
            fontSize = maxFontSize.value.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}