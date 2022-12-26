package com.example.aairastation.core.ui_util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.aairastation.R
import com.example.aairastation.destinations.MenuListScreenDestination
import com.example.aairastation.destinations.OrderListScreenDestination
import com.example.aairastation.destinations.SettingsScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction

enum class BottomNavItems(
    val direction: Direction
) {
    Orders(OrderListScreenDestination) {
        @Composable
        override fun icon(): ImageVector = ImageVector.vectorResource(id = R.drawable.order)
    },
    Menu(MenuListScreenDestination) {
        @Composable
        override fun icon(): ImageVector = ImageVector.vectorResource(id = R.drawable.menu)
    },
    Settings(SettingsScreenDestination) {
        @Composable
        override fun icon(): ImageVector = ImageVector.vectorResource(id = R.drawable.settings)
    };

    @Composable
    abstract fun icon(): ImageVector
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
                onClick = { if (!selected) navigator.navigate(item.direction) },
                modifier = Modifier.background(color = MaterialTheme.colors.primary),
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(4.dp),
                    ) {
                        val itemColor = MaterialTheme.colors.onPrimary

                        if (selected) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Icon(
                                imageVector = item.icon(),
                                contentDescription = item.name,
                                modifier = Modifier.weight(1f),
                                tint = itemColor,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.body2,
                                color = itemColor,
                            )
                        } else {
                            Icon(
                                imageVector = item.icon(),
                                contentDescription = item.name,
                                modifier = Modifier.fillMaxHeight(0.5f),
                                tint = itemColor,
                            )
                        }
                    }
                }
            )
        }
    }
}