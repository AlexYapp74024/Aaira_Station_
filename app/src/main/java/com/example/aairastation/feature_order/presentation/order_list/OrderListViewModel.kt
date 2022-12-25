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
                // An order is current when at least 1 entry is not completed.
                // That is, is at least one detail is not completed
                !details.fold(true) { acc, detail ->
                    detail.completed && acc
                }
            }
        }

    val completedOrders: Flow<Map<FoodOrder, List<OrderDetail>>> =
        allOrders.map { map ->
            map.filter { (_, details) ->
                // An order is completed when at all entry completed.
                details.fold(true) { acc, detail ->
                    detail.completed && acc
                }
            }
        }
}