package com.example.aairastation.feature_order.presentation.order_menu

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aairastation.destinations.CheckOutScreenDestination
import com.example.aairastation.feature_menu.domain.MenuUseCases
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class OrderMenuListViewModel @Inject constructor(
    private val useCases: MenuUseCases
) : ViewModel() {

    /**
     * Stores the current list of items, initially empty
     */
    private val items = MutableStateFlow<Map<Food, Flow<Bitmap?>>>(mapOf())

    init {
        refreshItem()
    }

    /**
     * Retrieves item from the database
     */
    fun refreshItem() = viewModelScope.launch {
        useCases.getAllFood
            .withImages(scope = viewModelScope)
            .collect {
                items.value = it
            }
    }

    /**
     * Sort food items by category
     */
    val itemsAndCategories: Flow<Map<FoodCategory, Map<Food, Flow<Bitmap?>>>> =
        items.map { items ->
            items.toList().groupBy { (food, _) ->
                food.category
            }.map { (category, list) ->
                category to list.toMap()
            }.toMap().filter { (_, items) ->
                // Remove categories without items
                items.isNotEmpty()
            }
        }

    /**
     * A hash map used to remember the user's order
     */
    private val _foodQuantity = MutableStateFlow<Map<Food, Int>>(mapOf())
    val foodQuantity = _foodQuantity.asStateFlow()

    fun reviewOrder(food: Food) {
        val mutableMap = _foodQuantity.value.toMutableMap()
        mutableMap[food] = (mutableMap[food] ?: 0) + 1
        _foodQuantity.value = mutableMap
    }

    fun incrementFood(food: Food) {
        val mutableMap = _foodQuantity.value.toMutableMap()
        mutableMap[food] = (mutableMap[food] ?: 0) + 1
        _foodQuantity.value = mutableMap
    }

    fun deleteFood(food: Food) {
        val mutableMap = _foodQuantity.value.toMutableMap()
        mutableMap.remove(food)
        _foodQuantity.value = mutableMap
    }
    
    private val jsonFormatter = Json { allowStructuredMapKeys = true }

    /**
     * Encodes the food order into a json representation before sending it to the check out screen
     */
    fun submitOrder(navigator: DestinationsNavigator) {
        val json = jsonFormatter.encodeToString(foodQuantity.value)
        navigator.navigate(CheckOutScreenDestination(json))
    }
}