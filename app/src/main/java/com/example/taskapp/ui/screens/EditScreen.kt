package com.example.taskapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.taskapp.data.local.entity.Task
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun EditScreen(onDismiss: () -> Unit,
               onConfirm: (String, String, LocalDate) -> Unit,
               onDelete: (() -> Unit)? = null,
               task: Task? = null) {
    var title by remember { mutableStateOf(task?.title ?: "") }
    var description by remember { mutableStateOf(task?.description ?: "") }
    var priority by remember { mutableIntStateOf(task?.priority ?: 5) }
    var dueDate by remember { mutableStateOf(task?.dueDate ?: LocalDate.now().plusDays(30)) }

    val scrollState = rememberScrollState()

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = dueDate
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        dueDate = Instant.ofEpochMilli(it)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    AlertDialog(onDismissRequest = onDismiss,
        title = { Text(if (task == null) "Add Task" else "Edit Task")},
        text = {
            Column(modifier = Modifier.verticalScroll(scrollState)
                    .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = title,
                    onValueChange = { title = it },                    label = { Text("Title")},
                    modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = description,
                    onValueChange = { description = it },
                    label = { Text("Description")},
                    modifier = Modifier.fillMaxWidth())
                Text(text = "Due date: $dueDate",
                    style = MaterialTheme.typography.bodyMedium)
                OutlinedButton(onClick = { showDatePicker = true }) {
                    Text("Change due date")
                }

                if (task != null && onDelete != null) {
                    Button(onClick = onDelete,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
                        Text("Delete task")
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(title, description, dueDate) }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        })
}