package com.example.aairastation.core.ui_util

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import com.example.aairastation.destinations.MenuListScreenDestination
import com.example.aairastation.destinations.OrderListScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction

enum class BottomNavItems(
    val icon: ImageVector,
    val direction: Direction
) {
    Orders(Icons.Default.Menu, OrderListScreenDestination),
    Menu(Icons.Default.Menu, MenuListScreenDestination),
}

@Composable
fun DefaultBottomNavigation(
    currentItem: BottomNavItems,
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        BottomNavItems.values().forEach { item ->
            val selected = item == currentItem
            BottomNavigationItem(
                selected = selected,
                onClick = { navigator.navigate(item.direction) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.name
                    )
                    if (selected) {
                        Text(
                            text = item.name,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            )
        }
    }
}