package com.example.aairastation.feature_order.presentation.order_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aairastation.core.formatPriceToRM
import com.example.aairastation.core.formatTo2dp
import com.example.aairastation.core.ui_util.BottomNavItems
import com.example.aairastation.core.ui_util.DefaultBottomNavigation
import com.example.aairastation.core.ui_util.DefaultTopAppBar
import com.example.aairastation.destinations.CompletedOrderDetailScreenDestination
import com.example.aairastation.destinations.CurrentOrderDetailScreenDestination
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.priceInRinggit
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.NumberedTable
import com.example.aairastation.feature_order.domain.model.OrderDetail
import com.example.aairastation.ui.theme.AairaStationTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

private lateinit var viewModel: OrderListViewModel
private var navigator: DestinationsNavigator = EmptyDestinationsNavigator

/**
 * [RootNavGraph] means that this is the first screen that is shown when the app is launched
 * RootNavGraph means that this is the first screen that is shown when the app is launched
 */
@RootNavGraph(start = true)
@Destination
@Composable
fun OrderListScreen(navigatorIn: DestinationsNavigator) {

    viewModel = hiltViewModel()
    navigator = navigatorIn

    // Extract data from viewModels
    val currentOrder by viewModel.currentOrders.collectAsState(initial = mapOf())
    val completedOrder by viewModel.completedOrders.collectAsState(initial = mapOf())

    OrderList(
        currentOrder = currentOrder,
        completedOrder = completedOrder,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OrderList(
    currentOrder: Map<FoodOrder, List<OrderDetail>>,
    completedOrder: Map<FoodOrder, List<OrderDetail>>,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = "Orders",
                canNavigateBack = false,
                navigateUp = {},
            )
        },
        bottomBar = {
            DefaultBottomNavigation(
                currentItem = BottomNavItems.Orders,
                navigator = navigator,
            )
        }
    ) { padding ->
        LazyColumn(modifier = modifier.padding(padding)) {
            stickyHeader {
                Box(
                    modifier = Modifier
                        .background(color = MaterialTheme.colors.background)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Current Order",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(16.dp),
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            /**
             * Shows the list of current orders
             */
            if (currentOrder.isNotEmpty()) {
                itemsIndexed(currentOrder.toList()) { index, (order, details) ->
                    val bgColor = if (index % 2 == 0) {
                        MaterialTheme.colors.primaryVariant
                    } else {
                        MaterialTheme.colors.background
                    }

                    Box(
                        modifier = Modifier
                            .background(color = bgColor)
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                    ) {
                        CurrentOrderList(details, modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navigator.navigate(CurrentOrderDetailScreenDestination(orderID = order.orderId))
                            })
                    }
                }
            } else {
                item {
                    Text(
                        text = "Currently no orders",
                        modifier = Modifier.padding(16.dp),
                    )
                }
            }

            stickyHeader {
                Box(
                    modifier = Modifier
                        .background(color = MaterialTheme.colors.background)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Completed Order",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(16.dp),
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            /**
             * Shows the list of completed orders
             */
            if (completedOrder.isNotEmpty()) {
                itemsIndexed(completedOrder.toList()) { index, (order, details) ->
                    val bgColor = if (index % 2 == 0) {
                        MaterialTheme.colors.primaryVariant
                    } else {
                        MaterialTheme.colors.background
                    }

                    Box(
                        modifier = Modifier
                            .background(color = bgColor)
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                    ) {
                        CompletedOrderList(details, modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navigator.navigate(CompletedOrderDetailScreenDestination(orderID = order.orderId))
                            })
                    }
                }
            } else {
                item {
                    Text(
                        text = "Currently no orders",
                        modifier = Modifier.padding(16.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun CurrentOrderList(
    details: List<OrderDetail>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        details.onEach { detail ->
            with(detail) {
                Text(
                    text = "${food.foodName}    x$amount",
                )
            }
        }
    }
}

@Composable
private fun CompletedOrderList(
    details: List<OrderDetail>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        details.onEach { detail ->
            with(detail) {
                Row {
                    Text(
                        text = "${food.foodName}    x$amount",
                        color = Color.Black.copy(alpha = .5f),
                        modifier = Modifier.weight(1f),
                    )

                    Text(
                        text = (food.priceInRinggit * amount).formatTo2dp(),
                        color = Color.Black.copy(alpha = .5f),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        val total = (details.toList().foldRight(0) { detail, acc ->
            acc + (detail.food.priceInCents * detail.amount)
        } / 100.0).formatPriceToRM()

        Row {
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Total: $total",
                color = Color.Black.copy(alpha = .5f),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderListPreview() {
    val orderList = listOf(
        OrderDetail(1, FoodOrder.example, Food.example, 1),
        OrderDetail(1, FoodOrder.example, Food.example, 1),
        OrderDetail(1, FoodOrder.example, Food.example, 1),
        OrderDetail(1, FoodOrder.example, Food.example, 1),
    )

    val orderLists = mapOf(
        FoodOrder(1, NumberedTable.example) to orderList,
        FoodOrder(2, NumberedTable.example) to orderList,
        FoodOrder(3, NumberedTable.example) to orderList,
        FoodOrder(4, NumberedTable.example) to orderList,
    )

    AairaStationTheme {
        OrderList(currentOrder = orderLists, completedOrder = orderLists)
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyOrderListPreview() {
    val orderList = mapOf<FoodOrder, List<OrderDetail>>()

    AairaStationTheme {
        OrderList(currentOrder = orderList, completedOrder = orderList)
    }
}