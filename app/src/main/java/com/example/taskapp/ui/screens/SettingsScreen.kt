package com.example.taskapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.taskapp.R

@Composable
fun SettingsScreen(isDarkTheme: Boolean, onDarkModeToggle: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(stringResource(R.string.settings), style = MaterialTheme.typography.headlineLarge)
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Dark mode")
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { onDarkModeToggle() })
        }
        /*
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Text("Language")
            IconButton(onClick = { expanded = !expanded }) {
                Icon(Icons.Default.Abc, contentDescription = "Language Menu")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("English") },
                    onClick = {

                    })
                DropdownMenuItem(
                    text = { Text("Finnish") },
                    onClick = {

                    })
                DropdownMenuItem(
                    text = { Text("Swedish") },
                    onClick = {
                    })
                DropdownMenuItem(
                    text = { Text("Meänkieli") },
                    onClick = {

                    })
            }


        }*/
    }
}
