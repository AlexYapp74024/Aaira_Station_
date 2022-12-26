package com.example.aairastation.feature_menu.presentation.menu_list

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aairastation.feature_menu.domain.MenuUseCases
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MenuListViewModel @Inject constructor(
    private val useCases: MenuUseCases
) : ViewModel() {

    private val items = useCases.getAllFood.withImages(scope = viewModelScope)

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



    fun viewItem(navigator: DestinationsNavigator, id: Long) {
//        navigator.navigate(ForageItemDetailScreenDestination(id))
    }
}