package com.example.aairastation.feature_order.presentation.order_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.OrderDetail
import com.example.aairastation.feature_order.domain.use_case.OrderUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val useCases: OrderUseCases
) : ViewModel() {

    private val _order = MutableStateFlow<FoodOrder?>(FoodOrder.example)
    val order = _order.asStateFlow()

    private val _details = MutableStateFlow<List<OrderDetail>>(listOf())
    val details = _details.asStateFlow()

    fun retrieveOrders(orderId: Long) {
        viewModelScope.launch {
            retrieveOrdersSuspend(orderId)
        }
    }

    // Made specially to make unit Testing easier
    suspend fun retrieveOrdersSuspend(orderId: Long) {
        useCases.getOrder(orderId).collect {
            _order.value = it
        }
        useCases.getAllDetail().collect {
            _details.value = it.filter { detail ->
                detail.order.orderId == orderId
            }
        }
    }

    fun newOrder(order: FoodOrder, details: List<OrderDetail>) {
        viewModelScope.launch {
            val newID = useCases.insertOrder(order)
            details.forEach { detail ->
                useCases.insertDetail(detail)
            }

            retrieveOrders(newID)
        }
    }

    fun updateDetail(detail: OrderDetail) {

    }

    fun orderIsCompleted(): Boolean {
        return useCases.isOrderCompleted(_details.value)
    }
}