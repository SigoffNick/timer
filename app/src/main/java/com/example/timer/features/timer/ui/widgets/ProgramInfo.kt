package com.example.timer.features.timer.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.timer.core.extensions.toFormattedString
import kotlin.time.Duration

@Composable
fun ProgramInfo(modifier: Modifier = Modifier, workTime: Duration, restTime: Duration) {
    return Row(
        modifier = modifier.padding(horizontal = 16.dp),
    ) {
        Text(text = "Work", color = MaterialTheme.colorScheme.onPrimary)
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = workTime.toFormattedString(), color = MaterialTheme.colorScheme.onPrimary)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = "Example Icon",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = {})
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "Rest", color = MaterialTheme.colorScheme.onPrimary)
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = restTime.toFormattedString(), color = MaterialTheme.colorScheme.onPrimary)
    }
}