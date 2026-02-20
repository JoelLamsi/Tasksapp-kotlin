package com.example.taskapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.taskapp.data.local.entity.Task


@Composable
fun TaskRow(
    task: Task,
    onClick: () -> Unit,
    onToggleDone: () -> Unit,
    showDelete: Boolean,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = modifier.padding(vertical = 4.dp)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.done,
                onCheckedChange = { onToggleDone() })

            Column(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = task.dueDate.toString(),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(horizontal = 8.dp),
                color = MaterialTheme.colorScheme.secondary
            )

            if (showDelete) {
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete task",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}