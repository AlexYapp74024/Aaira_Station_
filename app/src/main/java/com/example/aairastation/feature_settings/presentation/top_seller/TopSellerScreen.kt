package com.example.aairastation.feature_settings.presentation.top_seller

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aairastation.core.ui_util.DefaultTopAppBar
import com.example.aairastation.core.ui_util.TopBarSelector
import com.example.aairastation.feature_settings.domain.model.TimeGrouping
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

private var navigator: DestinationsNavigator = EmptyDestinationsNavigator
private lateinit var viewModel: TopSellerViewModel

@Destination
@Composable
fun TopSellerScreen(navigatorIn: DestinationsNavigator) {
    navigator = navigatorIn
    viewModel = hiltViewModel()

    TopSellerScreen()
}

@Composable
fun TopSellerScreen() {
    TopSellerScaffold {
        TopSellerScreenContent()
    }
}

@Composable
fun TopSellerScreenContent() {
    val columnState = rememberLazyListState()

    TopBarSelector(
        items = TimeGrouping.values().toList(),
        itemToString = { it.groupName },
        lazyListState = columnState
    )
}

@Composable
fun TopSellerScaffold(
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = "Top Sellers",
                canNavigateBack = true,
                navigateUp = { navigator.navigateUp() })
        },
        modifier = modifier,
        content = content,
    )
}