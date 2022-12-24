package com.example.aairastation.feature_order.presentation

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.aairastation.core.formatPriceToRM
import com.example.aairastation.core.formatTo2dp
import com.example.aairastation.core.ui_util.DefaultTopAppBar
import com.example.aairastation.core.ui_util.ExposedDropdown
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.priceInRinggit
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.NumberedTable
import com.example.aairastation.feature_order.domain.model.OrderDetail
import com.example.aairastation.ui.theme.AairaStationTheme

@Composable
fun CheckOutScreen() {
}

@Composable
fun NewIncomingOrderScreen() {
}

@Composable
fun OldOrderDetailScreen() {
}

@Composable
fun OngoingOrderDetailScreen() {
}

@Composable
fun OrderDetailsScreen(
    title: String,
    order: FoodOrder,
    details: Map<OrderDetail, Food>,
    tables: List<NumberedTable>,
    modifier: Modifier = Modifier,
    showCheckBoxes: Boolean = false,
    canChangeTableNumber: Boolean = false,
    bottomButtons: @Composable () -> Unit = {},
) {
    var checkBoxSizePx by remember { mutableStateOf(IntSize.Zero) }
    val screenPixelDensity = LocalContext.current.resources.displayMetrics.density
    val checkBoxWidth = checkBoxSizePx.width / screenPixelDensity

    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = title,
                canNavigateBack = true,
                navigateUp = {},
            )
        },

        ) { padding ->
        Column {

            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .weight(1f)
            ) {

                item {
                    if (canChangeTableNumber) {
                        TableNumber(tables = tables)
                    } else {
                        Text(
                            text = "Table No:  ${order.tableID}",
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (showCheckBoxes) {
                            CheckBoxWithoutMargin(
                                //TODO Implement Check ALL
                                checked = true,
                                onCheckedChange = {},
                                modifier = modifier.onSizeChanged {
                                    checkBoxSizePx = it
                                }
                            )
                        }

                        Text(
                            text = "Items",
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f),
                        )

                        Text(
                            text = "Price (RM)",
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }

                item {
                    Divider(
                        color = Black, thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                items(details.toList()) { (detail, food) ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp),
                    ) {
                        if (showCheckBoxes) {
                            CheckBoxWithoutMargin(
                                checked = detail.completed,
                                onCheckedChange = { },
                                modifier = modifier.onSizeChanged {
                                    checkBoxSizePx = it
                                }
                            )
                        }

                        Text(text = food.name)

                        Text(
                            text = "    x${detail.amount}", fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f),
                        )

                        Text(
                            text = (food.priceInRinggit * detail.amount).formatTo2dp(),
                        )
                    }
                }

                item {
                    Divider(
                        color = Black, thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                item {
                    val total = (details.toList().foldRight(0) { (detail, food), acc ->
                        acc + (food.priceInCents * detail.amount)
                    } / 100.0).formatPriceToRM()

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp),
                    ) {
                        if (showCheckBoxes) {
                            Spacer(modifier = Modifier.width(checkBoxWidth.toInt().dp))
                        }

                        Text(
                            text = "Total",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f),
                        )

                        Text(text = total)
                    }
                }
            }

            bottomButtons()
        }
    }
}

@Composable
fun TableNumber(
    tables: List<NumberedTable>,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Table No:",
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.width(32.dp))

        ExposedDropdown(
            options = tables,
            onSelect = {},
            listItemToString = { it.id.toString() },
            label = {},
        )
    }
}


@Preview
@Composable
fun OrderDetailsScreenPreview1() {
    AairaStationTheme {
        OrderDetailsScreen(
            "App bar",
            FoodOrder(1, 12),
            mapOf(
                OrderDetail(1, 1, 3, 1) to Food.example,
                OrderDetail(2, 1, 3, 2) to Food.example,
                OrderDetail(3, 1, 3, 3) to Food.example,
                OrderDetail(4, 1, 3, 4) to Food.example,
            ),
            listOf(NumberedTable(1), NumberedTable(2), NumberedTable(3)),
            showCheckBoxes = true
        )
    }
}

@Preview
@Composable
fun OrderDetailsScreenPreview2() {
    AairaStationTheme {
        OrderDetailsScreen(
            "App bar",
            FoodOrder(1, 12),
            mapOf(
                OrderDetail(1, 1, 3, 1) to Food.example,
                OrderDetail(2, 1, 3, 2) to Food.example,
                OrderDetail(3, 1, 3, 3) to Food.example,
                OrderDetail(4, 1, 3, 4) to Food.example,
            ),
            listOf(NumberedTable(1), NumberedTable(2), NumberedTable(3)),
            showCheckBoxes = false,
            canChangeTableNumber = true,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CheckBoxWithoutMargin(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: CheckboxColors = CheckboxDefaults.colors(
        uncheckedColor = MaterialTheme.colors.onBackground,
        checkedColor = MaterialTheme.colors.onBackground.copy(alpha = 0.6f),
        checkmarkColor = MaterialTheme.colors.background,
    )
) {
    CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = modifier.padding(end = 12.dp),
            enabled = enabled,
            interactionSource = interactionSource,
            colors = colors,
        )
    }
}

