package com.example.aairastation.feature_order.presentation.order_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.NumberedTable
import com.example.aairastation.feature_order.domain.model.OrderDetail
import com.example.aairastation.feature_order.domain.use_case.OrderUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val useCases: OrderUseCases
) : ViewModel() {

    private val _order = MutableStateFlow<FoodOrder?>(null)
    val order = _order.asStateFlow()

    private val _detailsMap = MutableStateFlow<Map<Long, OrderDetail>>(mapOf())
    val details = _detailsMap.map { it.map { (_, detail) -> detail } }
        .stateIn(viewModelScope, SharingStarted.Lazily, listOf())

    private var _table = MutableStateFlow<List<NumberedTable>>(listOf())
    val tables = _table.asStateFlow()

    fun retrieveOrders(orderId: Long) = viewModelScope.launch {
        useCases.getAllDetail().collect { details ->
            details.filter { it.order.orderId == orderId }
                .onEach { updateDetail(it) }.also { list ->
                    if (list.isNotEmpty())
                        updateOrder(list.first().order)
                    else
                        updateOrder(null)
                }
        }
    }

    fun updateAll(order: FoodOrder, details: List<OrderDetail>) = viewModelScope.launch {
        updateOrder(order)
        details.forEach { updateDetail(it) }
    }

    fun updateOrder(order: FoodOrder?) = viewModelScope.launch {
        _order.value = order
    }

    fun updateDetail(detail: OrderDetail) = viewModelScope.launch {
        val mutableMap = _detailsMap.value.toMutableMap()
        mutableMap[detail.detailId] = detail

        _detailsMap.value = mutableMap
    }

    fun toggleDetailCompletion(detail: OrderDetail) = viewModelScope.launch {
        updateDetail(detail.copy(completed = !detail.completed))
    }

    fun saveOrder() = viewModelScope.launch {
        val newID = _order.value?.let { useCases.insertOrder(it) }
        this@OrderDetailViewModel.details.value.forEach { detail ->
            useCases.insertDetail(detail)
        }
        newID?.let { retrieveOrders(it) }
    }

    fun toggleAllOrders() = viewModelScope.launch {
        val orderState = orderIsCompleted()

        this@OrderDetailViewModel.details.value.map { detail ->
            detail.copy(completed = !orderState)
        }.forEach {
            updateDetail(it)
        }
    }

    suspend fun orderIsCompleted(): Boolean = useCases.isOrderCompleted(this.details.first())

    fun addAndSetNewTable(number: Long) = viewModelScope.launch {
        val newID =
            useCases.insertTable(NumberedTable(tableNumber = number))
        useCases.getTable(newID).collect { it ->
            it?.let {
                updateOrder(_order.value?.copy(table = it))
            }
        }
    }

    private val format = Json { allowStructuredMapKeys = true }

    fun parseFoodQuantity(foodQuantityJson: String) = viewModelScope.launch {
        val map = format.decodeFromString<Map<Food, Int>>(foodQuantityJson)
        _detailsMap.value = map.map { (food, amount) ->
            OrderDetail(order = FoodOrder.example, food = food, amount = amount)
        }.mapIndexed { index, detail ->
            index.toLong() to detail
        }.toMap()
    }

    fun submitNewOrder() = viewModelScope.launch {
        val newID = useCases.insertOrder(FoodOrder.example)
        val newOrder = useCases.getOrder(newID).first()!!

        details.value.map {
            it.copy(order = newOrder)
        }.onEach {
            useCases.insertDetail(it)
        }
    }
}