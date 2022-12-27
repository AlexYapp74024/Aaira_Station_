package com.example.aairastation.feature_menu.presentation.menu_list

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aairastation.destinations.EditFoodScreenDestination
import com.example.aairastation.feature_menu.domain.MenuUseCases
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditMenuListViewModel @Inject constructor(
    private val useCases: MenuUseCases
) : ViewModel() {

    private val items = MutableStateFlow<Map<Food, Flow<Bitmap?>>>(mapOf())

    init {
        refreshItem()
    }

    private fun refreshItem() = viewModelScope.launch {
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

    fun viewItem(navigator: DestinationsNavigator, id: Long) {
        navigator.navigate(EditFoodScreenDestination(id))
    }
}