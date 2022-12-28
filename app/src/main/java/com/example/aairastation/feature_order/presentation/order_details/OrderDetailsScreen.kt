package com.example.aairastation.feature_order.presentation.order_details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aairastation.core.formatPriceToRM
import com.example.aairastation.core.formatTo2dp
import com.example.aairastation.core.ui_util.DefaultTopAppBar
import com.example.aairastation.core.ui_util.ExposedDropdown
import com.example.aairastation.destinations.OrderListScreenDestination
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.priceInRinggit
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.NumberedTable
import com.example.aairastation.feature_order.domain.model.OrderDetail
import com.example.aairastation.ui.theme.AairaStationTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import kotlinx.coroutines.runBlocking

private lateinit var viewModel: OrderDetailViewModel
private var navigator: DestinationsNavigator = EmptyDestinationsNavigator
private var isCheckout = false

@Destination
@Composable
fun CheckOutScreen(
    foodQuantityJson: String,
    navigatorIn: DestinationsNavigator
) {
    navigator = navigatorIn
    viewModel = hiltViewModel()
    isCheckout = true

    viewModel.parseFoodQuantity(foodQuantityJson)

    OrderDetailsScreen(
        title = "Check out",
        canChangeTableNumber = true,
        bottomButtons = {
            Button(
                onClick = {
                    viewModel.submitNewOrder()
                    navigator.navigate(OrderListScreenDestination)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Save Changes")
            }
        }
    )
}

@Destination
@Composable
fun CompletedOrderDetailScreen(
    orderID: Long,
    navigatorIn: DestinationsNavigator
) {
    navigator = navigatorIn
    viewModel = hiltViewModel()
    viewModel.retrieveOrders(orderID)

    OrderDetailsScreen(title = "Order Detail")
}

@Destination
@Composable
fun CurrentOrderDetailScreen(
    orderID: Long,
    navigatorIn: DestinationsNavigator
) {
    navigator = navigatorIn
    viewModel = hiltViewModel()
    viewModel.retrieveOrders(orderID)

    OrderDetailsScreen(
        title = "Order Detail",
        showCheckBoxes = true,
        bottomButtons = {
            Button(
                onClick = {
                    viewModel.saveOrder()
                    navigator.navigate(OrderListScreenDestination)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Save Changes")
            }
        }
    )
}

@Composable
private fun OrderDetailsScreen(
    title: String,
    modifier: Modifier = Modifier,
    showCheckBoxes: Boolean = false,
    canChangeTableNumber: Boolean = false,
    bottomButtons: @Composable () -> Unit = {},
) {
    val order by viewModel.order.collectAsState(initial = FoodOrder(0, NumberedTable.example))
    val detail by viewModel.details.collectAsState(initial = listOf())
    val tables by viewModel.tables.collectAsState(initial = listOf())
    val table by viewModel.table.collectAsState(initial = NumberedTable.example)

    OrderDetailScreenScaffold(
        title = title,
        modifier = modifier,
    ) { padding ->
        OrderDetailsScreenContent(
            order = order,
            details = detail,
            tables = tables,
            table = table,
            modifier = Modifier.padding(padding),
            showCheckBoxes = showCheckBoxes,
            canChangeTableNumber = canChangeTableNumber,
            bottomButtons = bottomButtons,
        )
    }

}

@Composable
private fun OrderDetailsScreenContent(
    order: FoodOrder?,
    details: List<OrderDetail>,
    table: NumberedTable,
    tables: List<NumberedTable>,
    modifier: Modifier = Modifier,
    showCheckBoxes: Boolean = false,
    canChangeTableNumber: Boolean = false,
    bottomButtons: @Composable () -> Unit = {},
) {
    var checkBoxSizePx by remember { mutableStateOf(IntSize.Zero) }
    val screenPixelDensity = LocalContext.current.resources.displayMetrics.density
    val checkBoxWidth = checkBoxSizePx.width / screenPixelDensity

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        LazyColumn {
            item {
                if (canChangeTableNumber) {
                    ExposedDropdown(
                        options = tables,
                        modifier = Modifier.fillMaxWidth(),
                        listItemToString = {
                            if (it.tableNumber == 0L)
                                "Take Away"
                            else
                                it.tableNumber.toString()
                        },
                        value = table,
                        onSelect = { viewModel.setTable(it) },
                        label = { Text("Table Number") },
                    )
                } else {
                    val displayText = if (order?.table != null) {
                        "Table No:  ${order.table.tableId}"
                    } else {
                        "Take away"
                    }

                    Text(
                        text = displayText,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            if (isCheckout) item {
                ChangeCalculator(total(details), Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (showCheckBoxes) {
                        val checkAll = try {
                            runBlocking { viewModel.orderIsCompleted() }
                        } catch (_: Throwable) {
                            false
                        }

                        CheckBoxWithoutMargin(
                            checked = checkAll,
                            onCheckedChange = {
                                viewModel.toggleAllOrders()
                            },
                            modifier = modifier.onSizeChanged {
                                checkBoxSizePx = it
                            })
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
                    color = Black,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            items(details) { detail ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable {
                            if (showCheckBoxes) viewModel.toggleDetailCompletion(detail)
                        },
                ) {
                    if (showCheckBoxes) {
                        CheckBoxWithoutMargin(checked = detail.completed,
                            onCheckedChange = {},
                            modifier = modifier.onSizeChanged {
                                checkBoxSizePx = it
                            })
                    }

                    Text(text = detail.food.foodName)

                    Text(
                        text = "    x${detail.amount}", fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                    )

                    Text(
                        text = (detail.food.priceInRinggit * detail.amount).formatTo2dp(),
                    )
                }
            }

            item {
                Divider(
                    color = Black,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item {
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

                    Text(text = total(details).formatPriceToRM())
                }
            }

        }

        Spacer(modifier = Modifier.weight(1f))
        bottomButtons()
    }
}

@Composable
private fun ChangeCalculator(
    total: Double,
    modifier: Modifier = Modifier,
) {
    var change by remember {
        mutableStateOf("")
    }
    val changeAmount = change.toDoubleOrNull() ?: 0.0

    val focusManager = LocalFocusManager.current

    Row(modifier = Modifier) {
        OutlinedTextField(
            modifier = modifier.weight(1f),
            value = change,
            onValueChange = { value ->
                change = value
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number,
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            maxLines = 1,
            label = { Text(text = "Paid Amount") },
        )

        Spacer(modifier = Modifier.width(16.dp))

        OutlinedTextField(
            modifier = modifier.weight(1f),
            value = ( changeAmount - total).formatTo2dp(),
            onValueChange = {},

            label = { Text(text = "Change") },
        )
    }
}

private fun total(details: List<OrderDetail>) =
    details.toList().foldRight(0) { detail, acc ->
        acc + (detail.food.priceInCents * detail.amount)
    } / 100.0


@Composable
private fun OrderDetailScreenScaffold(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = title,
                canNavigateBack = true,
                navigateUp = { navigator.navigateUp() },
            )
        },
        modifier = modifier,
        content = content
    )
}

@Preview
@Composable
private fun Preview1() {
    AairaStationTheme {
        OrderDetailScreenScaffold(title = "App bar") {
            OrderDetailsScreenContent(
                FoodOrder(1, NumberedTable.example), listOf(
                    OrderDetail(1, FoodOrder.example, Food.example, 1),
                    OrderDetail(2, FoodOrder.example, Food.example, 2),
                    OrderDetail(3, FoodOrder.example, Food.example, 3),
                    OrderDetail(4, FoodOrder.example, Food.example, 4),
                ),
                tables = listOf(
                    NumberedTable(1, 1),
                    NumberedTable(2, 2),
                    NumberedTable(3, 3)
                ),
                table = NumberedTable.example,
                showCheckBoxes = true
            )
        }
    }
}

@Preview
@Composable
private fun Preview2() {
    isCheckout = true
    AairaStationTheme {
        OrderDetailScreenScaffold(title = "App bar") {
            OrderDetailsScreenContent(
                FoodOrder(1, NumberedTable.example),
                listOf(
                    OrderDetail(1, FoodOrder.example, Food.example, 1),
                    OrderDetail(2, FoodOrder.example, Food.example, 2),
                    OrderDetail(3, FoodOrder.example, Food.example, 3),
                    OrderDetail(4, FoodOrder.example, Food.example, 4),
                ),
                tables = listOf(
                    NumberedTable(1, 1),
                    NumberedTable(2, 2),
                    NumberedTable(3, 3)
                ),
                table = NumberedTable.example,
                showCheckBoxes = false,
                canChangeTableNumber = true,
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CheckBoxWithoutMargin(
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

