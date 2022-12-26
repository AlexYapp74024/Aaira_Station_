package com.example.aairastation.feature_order.presentation.order_menu

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aairastation.feature_menu.domain.MenuUseCases
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderMenuListViewModel @Inject constructor(
    private val useCases: MenuUseCases
) : ViewModel() {

    private val items = MutableStateFlow<Map<Food, Flow<Bitmap?>>>(mapOf())

    init {
        refreshItem()
    }

    fun refreshItem() = viewModelScope.launch {
        useCases.getAllFood
            .withImages(scope = viewModelScope)
            .collect {
                items.value = it
            }
    }

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

    private val foodQuantity = mutableStateMapOf<Food, Int>()

    fun incrementFood(food: Food) {
        foodQuantity[food] = (foodQuantity[food] ?: 0) + 1
    }

    fun deleteFood(food: Food) {
        foodQuantity.remove(food)
    }
}