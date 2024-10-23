package com.example.timer.features.timer.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.timer.core.extensions.toTwoDigitString
import kotlin.time.Duration

@Composable
fun Timer (duration: Duration, modifier: Modifier = Modifier) {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val fontSize = rememberSaveable(key = screenWidthDp.toString()) {
        mutableFloatStateOf(screenWidthDp * 0.38f)
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ShowTimeText(
            duration.inWholeMinutes.toTwoDigitString(),
            fontSize.floatValue
        )
        ShowDot(fontSize = fontSize.floatValue)
        ShowTimeText(
            (duration.inWholeSeconds % 60).toTwoDigitString(),
            fontSize.floatValue
        )
    }
}

@Composable
private fun ShowTimeText(
    timeCount: String,
    fontSize: Float
) {
    Text(
        text = timeCount,
        style = TextStyle(
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
private fun ShowDot(fontSize: Float) {
    Text(
        text = ":",
        style = TextStyle(
            fontSize = (fontSize).sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
    )
}
