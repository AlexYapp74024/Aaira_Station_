package com.example.aairastation.feature_order.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.OrderDetail

@Composable
fun OrderList(
    currentOrder: Map<FoodOrder, List<OrderDetail>>,
    completedOrder: Map<FoodOrder, List<OrderDetail>>,
    modifier: Modifier = Modifier,
) {
    Scaffold(

    ) { padding ->
        Column(modifier = modifier.padding(padding)) {

        }
    }
}