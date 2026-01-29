package com.example.taskapp.models

import androidx.compose.foundation.MutatePriority
import java.time.LocalDate

data class TaskUIState(val tasks: List<Task> = emptyList(),
                       val isLoading: Boolean = false,
                       val errorMessage: String? = null,
                       val newTitle: String = "",
                       val newDescription: String = "",
                       val newPriority: Int = 5,
                       val newDueDate: LocalDate = LocalDate.now().plusDays(30),
                       val filter: TaskFilter = TaskFilter.ALL
)