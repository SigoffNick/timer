package com.example.timer.experimental.home_page_experimental_design.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.timer.core.training_programs.BoxingProgram

@Composable
fun TrainingTypeSwitcher(
    items: List<BoxingProgram>,
    selectedItem: BoxingProgram,
    onItemSelected: (BoxingProgram) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(16.dp)
            .clickable { expanded.value = true },
        contentAlignment = Alignment.CenterStart,
    ) {
        Row {
            Text(text = selectedItem.name, color = Color.White)
            Icon(
                imageVector = if (expanded.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = "Dropdown Icon",
                tint = Color.White
            )
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelected(item)
                        expanded.value = false
                    },
                    text = {
                        Text(
                            text = item.name,
                        )
                    })
            }
        }
    }
}