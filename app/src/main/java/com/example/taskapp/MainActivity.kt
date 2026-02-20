package com.example.taskapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskapp.ui.theme.TaskappTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskapp.data.local.AppDatabase
import com.example.taskapp.data.repository.TaskRepository
import com.example.taskapp.ui.components.navigation.AppNavigation
import com.example.taskapp.viewmodels.TaskViewModel

class MainActivity : ComponentActivity() {
    private val database by lazy {
        AppDatabase.getDatabase(context = applicationContext)
    }

    private val repository by lazy {
        TaskRepository(database.taskDao())
    }

    private val viewModel: TaskViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T: ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return TaskViewModel(repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme by rememberSaveable { mutableStateOf(false) }

            TaskappTheme(darkTheme = isDarkTheme) {
                AppNavigation(viewModel, isDarkTheme) {
                    isDarkTheme = !isDarkTheme
                }
            }
        }
    }
}