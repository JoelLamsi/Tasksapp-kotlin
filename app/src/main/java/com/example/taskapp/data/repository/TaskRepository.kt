package com.example.taskapp.data.repository

import com.example.taskapp.data.local.dao.TaskDao
import com.example.taskapp.data.local.entity.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    val allTasks: Flow<List<Task>> = taskDao.getAll()

    fun getAllByDueDateDesc(): Flow<List<Task>> {
        return taskDao.getAllByDueDateDesc()
    }

    fun getDone(): Flow<List<Task>> {
        return taskDao.getDone()
    }

    fun getNotDone(): Flow<List<Task>> {
        return taskDao.getNotDone()
    }

    suspend fun insert(task: Task): Long {
        return taskDao.insert(task)
    }

    suspend fun updateTaskDoneStatus(task: Task) {
        taskDao.updateTaskDoneStatus(task.id, task.done)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }
}