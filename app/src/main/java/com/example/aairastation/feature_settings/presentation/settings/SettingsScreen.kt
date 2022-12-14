package com.example.aairastation.feature_settings.presentation.settings

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aairastation.R
import com.example.aairastation.core.ui_util.BottomNavItems
import com.example.aairastation.core.ui_util.DefaultBottomNavigation
import com.example.aairastation.core.ui_util.DefaultTopAppBar
import com.example.aairastation.destinations.EditListScreenDestination
import com.example.aairastation.destinations.SalesReportScreenDestination
import com.example.aairastation.destinations.TopSellerScreenDestination
import com.example.aairastation.ui.theme.AairaStationTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

private var navigator: DestinationsNavigator = EmptyDestinationsNavigator
private lateinit var viewModel: SettingScreenViewModel

@Destination
@Composable
fun SettingsScreen(navigatorIn: DestinationsNavigator) {
    navigator = navigatorIn
    viewModel = hiltViewModel()

    SettingsScaffold {
        SettingsScreenContent()
    }
}

/**
 * An Enum class is used cus it allows me it iterate thought its members
 *
 * @param itemName is the display name of the item
 * @param resId is the resource id of the image of the item's icon
 * @param onClick is a function that is called when the settings is selected
 */
enum class SettingsScreenItems(
    val itemName: String,
    @DrawableRes val resId: Int,
    val onClick: () -> Unit,
) {
    TopSeller("Top Seller", R.drawable.ranking, {
        navigator.navigate(TopSellerScreenDestination)
    }),
    SalesReport("Sales Report", R.drawable.calendar, {
        navigator.navigate(SalesReportScreenDestination)
    }),
    EditMenu("Edit Menu", R.drawable.edit, {
        navigator.navigate(EditListScreenDestination)
    }),
    LoadData("Load Preloaded Data", R.drawable.download, {
        viewModel.injectPreloadedData()
    })
}

@Composable
private fun SettingsScreenContent() {
    Column(modifier = Modifier.padding(16.dp)) {
        /**
         * Iterates through each items in the Enum class and displays them
         */
        SettingsScreenItems.values().forEach { it ->
            Row(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
                    .clickable {
                        it.onClick()
                    }
            ) {
                var textSizePx by remember { mutableStateOf(IntSize.Zero) }
                val screenPixelDensity = LocalContext.current.resources.displayMetrics.density
                val textHeight = textSizePx.height / screenPixelDensity

                Icon(
                    imageVector = ImageVector.vectorResource(id = it.resId),
                    contentDescription = null,
                    modifier = Modifier.height(textHeight.dp),
                    tint = MaterialTheme.colors.primary
                )

                Spacer(modifier = Modifier.width(32.dp))

                Text(
                    text = it.itemName,
                    modifier = Modifier.onSizeChanged { textSizePx = it },
                )
            }
        }
    }
}

@Composable
private fun SettingsScaffold(
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

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    AairaStationTheme {
        SettingsScaffold {
            SettingsScreenContent()
        }
    }
}
