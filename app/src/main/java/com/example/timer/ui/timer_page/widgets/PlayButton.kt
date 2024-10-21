package com.example.timer.ui.timer_page.widgets

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.timer.core.enums.CurrentProgramState
import com.example.timer.core.enums.StopwatchState

@Composable
fun PlayButton(
    onStart: () -> Unit,
    onCancel: () -> Unit,
    onStop: () -> Unit,
    currentProgramState: StopwatchState,
    isRunning: Boolean,
    modifier: Modifier = Modifier
) {

    val isSplit = currentProgramState != StopwatchState.Idle

    Box(modifier = modifier) {
        AnimatedVisibility(
            visible = !isSplit,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Button(
                onClick = onStart,
                modifier = Modifier.size(80.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = if (isRunning) Icons.Filled.Pause else  Icons.Filled.PlayArrow ,
                    contentDescription = "Play Icon",
                    tint = Color.Black
                )
            }
        }

        AnimatedVisibility(
            visible = isSplit,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Row {
                Button(
                    onClick = if (isRunning) onStop else onStart,
                    modifier = Modifier.size(80.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        imageVector = if (isRunning) Icons.Filled.Pause else  Icons.Filled.PlayArrow ,
                        contentDescription = "Play Icon",
                        tint = Color.Black
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = onCancel,
                    modifier = Modifier.size(80.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        imageVector = Icons.Filled.Stop,
                        contentDescription = "Stop Icon",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

@Preview()
@Composable
fun PlayButtonPreview() {
    PlayButton(
        onStart = {},
        onCancel = {},
        onStop = {},
        isRunning = true,
        currentProgramState = StopwatchState.Started
    )
}