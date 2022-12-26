package com.example.aairastation.core.ui_util

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun DefaultTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                title,
                color = MaterialTheme.colors.onPrimary,
                fontWeight = FontWeight.Bold
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
        navigationIcon = if (canNavigateBack) {
            {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack, contentDescription = null
                    )
                }
            }
        } else null
    )
}