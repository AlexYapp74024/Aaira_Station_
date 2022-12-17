package com.example.aairastation.core.ui_util

import android.widget.Toast
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun TopAppBarCompose() {
    val context = LocalContext.current

    TopAppBar(

        title = {
            Text(
                text = "Order", fontSize = 30.sp, maxLines = 1, overflow = TextOverflow.Ellipsis
            )
        },

        navigationIcon = {
            IconButton(onClick = {
                Toast.makeText(context, "Menu", Toast.LENGTH_SHORT).show()
            }) {
                Icon(Icons.Default.ArrowBack, "Menu")
            }
        }, actions = {
            IconButton(onClick = {
                Toast.makeText(context, "Search", Toast.LENGTH_SHORT).show()
            }) {
                Icon(Icons.Default.Search, "Menu")
            }

            IconButton(onClick = {
                Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show()
            }) {
                Icon(Icons.Default.Home, "Menu")
            }
        }, backgroundColor = Color.LightGray, contentColor = Color.Black
    )

}

@Composable
fun DefaultTopAppBar(
    Title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(Title) },
        modifier = modifier,
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