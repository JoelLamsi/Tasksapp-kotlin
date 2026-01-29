package com.example.taskapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.taskapp.models.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

class TaskViewModel : ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    private val _selectedTask = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> = _selectedTask

    init {
        _tasks.value = listOf(
            Task( title = "Fysiikan perusteet tietotekniikassa", description = "SI-järjestelmä", priority = 5, dueDate = LocalDate.of(2026, 1, 9), done =  false),
            Task(title = "Mobiiliohjelmointi natiiviteknologioilla", description = "Kotlin-perusteet", priority = 5, dueDate = LocalDate.of(2026, 1, 15), done = false),
            Task(title = "Web- ja hybriditeknologiat mobiiliohjelmoinnissa", description = "Tavoitesyke", priority = 5, dueDate = LocalDate.of(2026, 1, 14), done = true),
            Task(title = "Fysiikan perusteet tietotekniikassa", description = "Kinematiikka", priority = 5, dueDate = LocalDate.of(2026, 1, 14), done = false),
            Task(title = "Mobiiliohjelmointi natiiviteknologioilla", description = "Käyttöliittymä", priority = 5, dueDate = LocalDate.of(2026, 1, 21), done = false),
            Task(title = "Web- ja hybriditeknologiat mobiiliohjelmoinnissa", description = "Modal", priority = 5, dueDate = LocalDate.of(2026, 1, 21), done = false)
        )
    }

    fun addTask(task: Task)  {
        _tasks.value += task
    }

    fun updateTask(task: Task) {
        _tasks.value = _tasks.value.map {
            if (it.id == task.id) task else it
        }
    }

    fun toggleDone(id: String) {
        _tasks.value = _tasks.value.map {
            if (it.id == id)
                it.copy(done = !it.done) else it
        }
    }

    fun sortByDueDate(ascending: Boolean = true) {
        _tasks.value = if (ascending) {
            _tasks.value.sortedBy { it.dueDate }
        } else {
            _tasks.value.sortedByDescending { it.dueDate }
        }
    }

    fun removeTask(id: String) {
        _tasks.value = _tasks.value.filter { it.id != id }
    }

    fun selectTask(task: Task) {
        _selectedTask.value = task
    }

    fun closeDialog() {
        _selectedTask.value = null
    }
}