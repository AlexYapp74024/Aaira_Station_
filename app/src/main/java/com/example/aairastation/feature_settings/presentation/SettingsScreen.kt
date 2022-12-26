package com.example.aairastation.feature_settings.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.aairastation.core.ui_util.BottomNavItems
import com.example.aairastation.core.ui_util.DefaultBottomNavigation
import com.example.aairastation.core.ui_util.DefaultTopAppBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

private var navigator: DestinationsNavigator = EmptyDestinationsNavigator

@Destination
@Composable
fun SettingsScreen(navigatorIn: DestinationsNavigator) {
    navigator = navigatorIn

    SettingsScaffold {
        SettingsScreenContent()
    }
}

@Composable
fun SettingsScreenContent() {

}

@Composable
fun SettingsScaffold(
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(title = "Settings", canNavigateBack = false, navigateUp = {})
        },
        bottomBar = {
            DefaultBottomNavigation(
                currentItem = BottomNavItems.Settings,
                navigator = navigator
            )
        },
        modifier = modifier,
        content = content,
    )
}