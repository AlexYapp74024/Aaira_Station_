package com.example.aairastation.feature_order.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aairastation.core.formatPriceToRM
import com.example.aairastation.core.formatTo2dp
import com.example.aairastation.core.ui_util.DefaultTopAppBar
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.priceInRinggit
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.NumberedTable
import com.example.aairastation.feature_order.domain.model.OrderDetail
import com.example.aairastation.ui.theme.AairaStationTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination
@Composable
fun OrderListScreen() {
    OrderListPreview()
}

//TODO add on Click for items
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CurrentOrderList(
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

            if (currentOrder.isNotEmpty()) {
                itemsIndexed(currentOrder.toList()) { index, (_, details) ->
                    val bgColor = if (index % 2 == 0) {
                        MaterialTheme.colors.primary
                    } else {
                        MaterialTheme.colors.background
                    }

                    Box(
                        modifier = Modifier
                            .background(color = bgColor)
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        CurrentOrderList(details)
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

            if (completedOrder.isNotEmpty()) {
                itemsIndexed(completedOrder.toList()) { index, (_, details) ->
                    val bgColor = if (index % 2 == 0) {
                        MaterialTheme.colors.primary
                    } else {
                        MaterialTheme.colors.background
                    }

                    Box(
                        modifier = Modifier
                            .background(color = bgColor)
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        CompletedOrderList(details)
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
fun CurrentOrderList(
    details: List<OrderDetail>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(horizontal = 32.dp)) {
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
fun CompletedOrderList(
    details: List<OrderDetail>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(horizontal = 32.dp)) {
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
fun OrderListPreview() {
    val orderList = listOf(
        OrderDetail(1, FoodOrder.example, Food.example, 1),
        OrderDetail(1, FoodOrder.example, Food.example, 1),
        OrderDetail(1, FoodOrder.example, Food.example, 1),
        OrderDetail(1, FoodOrder.example, Food.example, 1),
    )

    val orderLists = mapOf(
        FoodOrder(1, NumberedTable.example, null) to orderList,
        FoodOrder(2, NumberedTable.example, null) to orderList,
        FoodOrder(3, NumberedTable.example, null) to orderList,
        FoodOrder(4, NumberedTable.example, null) to orderList,
    )

    AairaStationTheme {
        CurrentOrderList(currentOrder = orderLists, completedOrder = orderLists)
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyOrderListPreview() {
    val orderList = mapOf(
        FoodOrder(1, NumberedTable.example, null) to listOf<OrderDetail>()
    )

    AairaStationTheme {
        CurrentOrderList(currentOrder = orderList, completedOrder = orderList)
    }
}