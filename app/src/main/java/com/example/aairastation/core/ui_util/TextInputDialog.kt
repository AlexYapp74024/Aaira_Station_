package com.example.forage.core.ui_util

import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextInputDialog(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    dismissButton: @Composable (() -> Unit)?,
    onDismissRequest: () -> Unit,
    title: @Composable (() -> Unit)?,
    confirmButton: @Composable () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        text = {
            TextField(
                modifier = Modifier.padding(top = 4.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.background
                ),
                value = value,
                onValueChange = onValueChange
            )
        },
        onDismissRequest = onDismissRequest,
        title = title,
        confirmButton = confirmButton,
        dismissButton = dismissButton,
    )
}