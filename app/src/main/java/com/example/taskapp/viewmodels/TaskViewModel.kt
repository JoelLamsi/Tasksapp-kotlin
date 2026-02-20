package com.example.taskapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskapp.data.local.entity.Task
import com.example.taskapp.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _sortAscending = MutableStateFlow(true)

    val allTasks: StateFlow<List<Task>> = combine(
        repository.allTasks,
        _sortAscending
    ) { tasks, ascending ->
        if (ascending) {
            tasks.sortedBy { it.dueDate }
        } else {
            tasks.sortedByDescending { it.dueDate }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addTask(task: Task)  {
        viewModelScope.launch {
            repository.insert(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.insert(task)
        }
    }

    fun toggleDone(task: Task)  {
        viewModelScope.launch {
            repository.updateTaskDoneStatus(task.copy(done = !task.done))
        }
    }

    fun sortByDueDate(ascending: Boolean) {
        _sortAscending.value = ascending
    }

    fun removeTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}