package com.example.aairastation.feature_menu.presentation.view_models

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aairastation.feature_menu.domain.MenuUseCases
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuListViewModel @Inject constructor(
    private val useCases: MenuUseCases
) : ViewModel() {

    private var categories = MutableStateFlow<List<FoodCategory>>(listOf())
    private var bitmaps = MutableStateFlow<Map<Food, Flow<Bitmap?>>>(mapOf())

    var itemsAndCategories: Flow<Map<FoodCategory, Map<Food, Flow<Bitmap?>>>> =
        combine(categories, bitmaps) { categories, bitmaps ->
            categories.associateWith { category ->
                bitmaps.filter { (food, _) ->
                    food.category == category
                }
            }.toMutableMap().also { map ->
                // Append food without categories
                map[FoodCategory.noCategory] = bitmaps.filter { (item, _) -> item.category == null }
            }.filter { (_, items) ->
                // Remove categories without items
                items.isNotEmpty()
            }
        }

    fun viewItem(navigator: DestinationsNavigator, id: Int) {
//        navigator.navigate(ForageItemDetailScreenDestination(id))
    }


    init {
        refreshItems()
    }

    private var retrieveItemJob: Job? = null
    fun refreshItems() {
        retrieveItemJob?.cancel()
        retrieveItemJob = viewModelScope.launch {
            refreshItemsSuspend()
        }
    }

    suspend fun refreshItemsSuspend() {
        getCategories()
        getBitmap()
    }

    private suspend fun getCategories() {
        categories.value = useCases.getAllFoodCategory().first()
    }

    private suspend fun getBitmap() {
        useCases.getAllFood.withImages(
            scope = viewModelScope
        ).collect {
            bitmaps.value = it
        }
    }
}