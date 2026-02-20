package com.example.taskapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.taskapp.data.local.entity.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Upsert
    suspend fun insert(task: Task): Long

    @Query("SELECT * FROM tasks ORDER BY due_date ASC")
    fun getAll():
            Flow<List<Task>>

    @Query("SELECT * FROM tasks ORDER BY due_date DESC")
    fun getAllByDueDateDesc(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE is_done = 1")
    fun getDone(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE is_done = 0")
    fun getNotDone(): Flow<List<Task>>

    @Query("UPDATE tasks SET is_done = :isDone WHERE id = :taskId")
    suspend fun updateTaskDoneStatus(taskId: Int, isDone: Boolean)

    @Delete
    suspend fun deleteTask(task: Task)
}