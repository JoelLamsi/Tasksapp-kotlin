package com.example.taskapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.taskapp.R
import com.example.taskapp.data.local.entity.Task
import com.example.taskapp.viewmodels.TaskViewModel

@Composable
fun TaskRow(
    task: Task,
    onClick: () -> Unit,
    modifier: Modifier = Modifier) {

    Row(
        modifier = modifier.padding(vertical = 4.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textDecoration = if (task.done) TextDecoration.LineThrough else TextDecoration.None
            )

            Text(text = task.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textDecoration = if (task.done) TextDecoration.LineThrough else TextDecoration.None)
        }
    }
}

@Composable
fun CalendarScreen(viewModel: TaskViewModel) {
    val tasks by viewModel.allTasks.collectAsState()
    var selectedTask by remember { mutableStateOf<Task?>(null) }
    val groupedByDates = tasks.sortedBy { it.dueDate }.groupBy { it.dueDate ?: "No date" }
    var showEditDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(stringResource(R.string.calendar), style = MaterialTheme.typography.headlineLarge)

        LazyColumn { groupedByDates.forEach { (date, tasks) ->
            item {
                Text(text = date.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 12.dp))
            }
            items(items = tasks, key = { it.id }) { task ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                    TaskRow(modifier = Modifier.fillMaxWidth().padding(8.dp),
                        task = task,
                        onClick = {
                            selectedTask = task
                            showEditDialog = true
                        })
                }
            }
        }}
    }

    if (showEditDialog) {
        EditScreen(
            task = selectedTask,
            onDismiss = { showEditDialog = false },
            onConfirm = { title, desc, dueDate ->
                if (selectedTask != null) {
                    viewModel.updateTask(
                        selectedTask!!.copy(
                            title = title,
                            description = desc,
                            dueDate = dueDate
                        )
                    )
                } else {
                    viewModel.addTask(
                        Task(
                            title = title,
                            description = desc,
                            priority = 5,
                            dueDate = dueDate,
                            done = false
                        )
                    )
                }
                showEditDialog = false
            },
            onDelete = {
                viewModel.removeTask(selectedTask!!)
                showEditDialog = false
            })
    }

}
