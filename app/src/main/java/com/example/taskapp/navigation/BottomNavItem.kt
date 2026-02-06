package com.example.taskapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String,
                           val icon: ImageVector,
                           val label: String) {
    object Home: BottomNavItem(ROUTE_HOME, Icons.Default.Home, "Home")
    object Calendar: BottomNavItem(ROUTE_CALENDAR, Icons.Default.CalendarMonth, "Calendar")
    //object Settings: BottomNavItem(ROUTE_SETTINGS, Icons.Default.Settings, "Settings")
}