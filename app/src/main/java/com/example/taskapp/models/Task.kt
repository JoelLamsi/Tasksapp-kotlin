package com.example.taskapp.models

import java.time.LocalDate
import java.util.UUID

data class Task(val id: String = UUID.randomUUID().toString(),
                val title: String,
                val description: String,
                val priority: Int,
                val dueDate: LocalDate,
                val done: Boolean = false)

enum class TaskFilter {
    ALL, DONE, NOTDONE
}