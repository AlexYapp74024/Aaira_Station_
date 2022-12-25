package com.example.aairastation.feature_order.presentation.order_list

import androidx.lifecycle.ViewModel
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.OrderDetail
import com.example.aairastation.feature_order.domain.use_case.OrderUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class OrderListViewModel @Inject constructor(
    useCases: OrderUseCases
) : ViewModel() {

    private val allOrders = useCases.getAllDetail().map { list ->
        list.groupBy { detail -> detail.order }
    }

    val currentOrders: Flow<Map<FoodOrder, List<OrderDetail>>> =
        allOrders.map { map ->
            map.filter { (_, details) ->
                !useCases.isOrderCompleted(details)
            }
        }

    val completedOrders: Flow<Map<FoodOrder, List<OrderDetail>>> =
        allOrders.map { map ->
            map.filter { (_, details) ->
                useCases.isOrderCompleted(details)
            }
        }
}