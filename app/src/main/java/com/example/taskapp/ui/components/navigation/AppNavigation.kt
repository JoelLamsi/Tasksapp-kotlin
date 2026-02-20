package com.example.taskapp.ui.components.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.taskapp.ui.screens.CalendarScreen
import com.example.taskapp.ui.screens.HomeScreen
import com.example.taskapp.ui.screens.SettingsScreen
import com.example.taskapp.viewmodels.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(viewModel: TaskViewModel,
                  isDarkTheme: Boolean,
                  onDarkModeToggle: () -> Unit) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Calendar
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Task App") },
                navigationIcon = {
                    if (currentRoute == ROUTE_SETTINGS) {
                        IconButton(onClick = { navController.popBackStack()}) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                actions = {
                    if (currentRoute != ROUTE_SETTINGS) {
                        IconButton(onClick = {
                            navController.navigate(ROUTE_SETTINGS)
                        })
                        {
                            Icon(imageVector = Icons.Default.Settings,
                                contentDescription = "Settings")
                        }
                }})
        },
        bottomBar = {
            if (currentRoute != ROUTE_SETTINGS) {
                NavigationBar() {
                    navItems.forEach { item ->
                        NavigationBarItem(selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) })
                    }
                }
            }
        } ) {
        paddingValues ->
        NavHost(navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(paddingValues)) {
            composable(BottomNavItem.Home.route) {
                HomeScreen(viewModel)
            }
            composable(BottomNavItem.Calendar.route) { CalendarScreen(viewModel) }
            composable(ROUTE_SETTINGS) { SettingsScreen(isDarkTheme = isDarkTheme,
                onDarkModeToggle = onDarkModeToggle) }
        }
    }
}