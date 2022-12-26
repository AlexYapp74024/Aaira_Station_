package com.example.aairastation.feature_order.presentation.order_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.OrderDetail
import com.example.aairastation.feature_order.domain.use_case.OrderUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val useCases: OrderUseCases
) : ViewModel() {

    private val _order = MutableStateFlow<FoodOrder?>(FoodOrder.example)
    val order = _order.asStateFlow()

    private val _detailsMap = MutableStateFlow<Map<Long, OrderDetail>>(mapOf())
    private val _details = _detailsMap.map { it.map { (_, detail) -> detail } }
        .stateIn(viewModelScope, SharingStarted.Lazily, listOf())
    val details = _details

    // Made specially to make unit Testing easier
    fun retrieveOrders(orderId: Long) = viewModelScope.launch {
        useCases.getOrder(orderId).collect {
            updateOrder(it)
        }
        useCases.getAllDetail().collect { details ->
            details.filter { it.order.orderId == orderId }
                .forEach { updateDetail(it) }
        }
    }

    fun updateAll(order: FoodOrder, details: List<OrderDetail>) {
        updateOrder(order)
        details.forEach { updateDetail(it) }
    }

    fun updateOrder(order: FoodOrder?) {
        _order.value = order
    }

    fun updateDetail(detail: OrderDetail) {
        val mutableMap = _detailsMap.value.toMutableMap()
        mutableMap[detail.detailId] = detail

        _detailsMap.value = mutableMap
    }

    fun saveOrder() = viewModelScope.launch {
        val newID = _order.value?.let { useCases.insertOrder(it) }
        details.value.forEach { detail ->
            useCases.insertDetail(detail)
        }
        newID?.let { retrieveOrders(it) }
    }

//    fun orderIsCompleted(): Boolean = useCases.isOrderCompleted(_details)
}