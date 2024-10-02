package com.example.timer.ui.timer_page.widgets


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.timer.core.training_programs.BoxingProgram
import kotlin.time.Duration


@Composable
fun ProgramDescription(selectedProgram: BoxingProgram, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Description(title = "Работа", time = selectedProgram.workDuration)
        Description(title = "Отдых", time = selectedProgram.restDuration)
    }
}

@Composable
fun Description(title: String, time: Duration) {
    Row {
        Text(
            text = title,
            color = Color.White,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = time.toMinutesSeconds(),
            color = Color.White,
        )
    }
}

fun Duration.toMinutesSeconds(): String {
    val minutes = this.inWholeMinutes
    val seconds = this.inWholeSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}