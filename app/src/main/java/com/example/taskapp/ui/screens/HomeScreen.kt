package com.example.taskapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.example.taskapp.ui.components.TaskRow
import com.example.taskapp.R
import com.example.taskapp.data.local.entity.Task
import com.example.taskapp.data.local.entity.TaskFilter
import com.example.taskapp.viewmodels.TaskViewModel


@Composable
fun HomeScreen(viewModel: TaskViewModel) {
    val tasks by viewModel.allTasks.collectAsState()
    var selectedFilter by remember { mutableStateOf(TaskFilter.ALL) }
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<Task?>(null) }
    var sortAscending by remember { mutableStateOf(true) }
    var showDeleteButtons by remember { mutableStateOf(false) }

    val filteredTasks = remember(tasks, selectedFilter) {
        tasks.filter { task ->
            when (selectedFilter) {
                TaskFilter.ALL -> true
                TaskFilter.NOTDONE -> !task.done
                TaskFilter.DONE -> task.done
            }
        }
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            selectedTask = null
            showEditDialog = true
        }) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxWidth().padding(innerPadding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.tasks),
                style = MaterialTheme.typography.headlineLarge
            )

            // Painikkeet valmiiden, keskeneräisten ja kaikkien tehtävien näyttämiseen
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TaskFilter.entries.forEach { filter: TaskFilter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        label = { Text(filter.name) })
                }
            }

            // Lajittelu & Kytkin tehtävien poistamiselle
            Row(
                modifier = Modifier.padding(top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = {
                    if (sortAscending) {
                        viewModel.sortByDueDate(ascending = true)
                    } else {
                        viewModel.sortByDueDate(ascending = false)
                    }
                    sortAscending = !sortAscending
                }) {
                    Text(if (sortAscending) "Sort Ascending" else "Sort Descending")
                }

                Button(
                    onClick = {
                        showDeleteButtons = !showDeleteButtons
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text(
                        text = "Delete Tasks",
                        style = LocalTextStyle.current.copy(
                            textDecoration = if (showDeleteButtons) {
                                TextDecoration.Underline
                            } else {
                                TextDecoration.None
                            }
                        )
                    )
                }
            }

            // Tehtävälistan esittäminen
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(items = filteredTasks, key = { it.id }) { task ->
                    TaskRow(
                        modifier = Modifier.fillMaxWidth(),
                        task = task,
                        onClick = {
                            selectedTask = task
                            showEditDialog = true
                        },
                        onToggleDone = { viewModel.toggleDone(task) },
                        onDelete = { viewModel.removeTask(task) },
                        showDelete = showDeleteButtons
                    )
                }
            }
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
}
