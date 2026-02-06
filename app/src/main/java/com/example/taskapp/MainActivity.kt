package com.example.taskapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.taskapp.ui.theme.TaskappTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskapp.navigation.AppNavigation
import com.example.taskapp.viewmodels.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: TaskViewModel = viewModel()
            var isDarkTheme by rememberSaveable { mutableStateOf(false) }

            TaskappTheme(darkTheme = isDarkTheme) {
                AppNavigation(viewModel, isDarkTheme) {
                    isDarkTheme = !isDarkTheme
                }
            }


        }
    }
}