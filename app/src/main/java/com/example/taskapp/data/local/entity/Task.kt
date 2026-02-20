package com.example.taskapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.UUID

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "priority")
    val priority: Int,
    @ColumnInfo(name = "due_date")
    val dueDate: LocalDate,
    @ColumnInfo(name = "is_done")
    val done: Boolean = false)

enum class TaskFilter {
    ALL, DONE, NOTDONE
}