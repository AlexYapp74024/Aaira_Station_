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

    /**
     * The current order
     */
    private val _order = MutableStateFlow<FoodOrder?>(null)
    val order = _order.asStateFlow()

    /**
     * Tag orderDetails by their id for easy access
     */
    private val _detailsMap = MutableStateFlow<Map<Long, OrderDetail>>(mapOf())
    val details = _detailsMap.map { it.map { (_, detail) -> detail } }
        .stateIn(viewModelScope, SharingStarted.Lazily, listOf())

    /**
     * The current table
     */
    private var _table = MutableStateFlow(NumberedTable.example)
    val table = _table.asStateFlow()

    /**
     * The list of tables that can be selected from
     */
    val tables = useCases.getAllTable().stateIn(viewModelScope, SharingStarted.Lazily, listOf())

    /**
     * Retrieve data from database
     */
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

    private fun updateOrder(order: FoodOrder?) = viewModelScope.launch {
        _order.value = order
    }


    fun updateDetail(detail: OrderDetail) = viewModelScope.launch {
        val mutableMap = _detailsMap.value.toMutableMap()
        mutableMap[detail.detailId] = detail

        _detailsMap.value = mutableMap
    }

    /**
     * Toggle if the food item is completed
     */
    fun toggleDetailCompletion(detail: OrderDetail) = viewModelScope.launch {
        updateDetail(detail.copy(completed = !detail.completed))
    }

    /**
     * Update the order in the database
     */
    fun saveOrder() = viewModelScope.launch {
        /**
         * When an item is inserted, it will return the its when inserted into the database
         */
        val newID = _order.value?.let { useCases.insertOrder(it) }
        this@OrderDetailViewModel.details.value.forEach { detail ->
            useCases.insertDetail(detail)
        }

        /**
         * Update the order state from new data from the database
         */
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

    fun setTable(table: NumberedTable) {
        _table.value = table
    }

    private val jsonFormatter = Json { allowStructuredMapKeys = true }

    /**
     * Parse Json data from OrderMenuListScreen
     */
    fun parseFoodQuantity(foodQuantityJson: String) = viewModelScope.launch {
        val map = jsonFormatter.decodeFromString<Map<Food, Int>>(foodQuantityJson)
        _detailsMap.value = map.map { (food, amount) ->
            OrderDetail(order = FoodOrder.example, food = food, amount = amount)
        }.mapIndexed { index, detail ->
            index.toLong() to detail
        }.toMap()
    }

    fun submitNewOrder() = viewModelScope.launch {
        val newID = useCases.insertOrder(FoodOrder.example.copy(table = table.value))
        val newOrder = useCases.getOrder(newID).first()!!

        details.value.map {
            /**
             * Associate each details with the new order inserted since the ID might be different
             */
            it.copy(order = newOrder)
        }.onEach {
            useCases.insertDetail(it)
        }
    }
}