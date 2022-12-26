package com.example.aairastation.feature_menu.presentation.menu_list

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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuListViewModel @Inject constructor(
    private val useCases: MenuUseCases
) : ViewModel() {

    private val items = MutableStateFlow<Map<Food, Flow<Bitmap?>>>(mapOf())

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
//        navigator.navigate(ForageItemDetailScreenDestination(id))
    }


    init {
        refreshItems()
    }

    private var retrieveItemJob: Job? = null
    fun refreshItems() {
        retrieveItemJob?.cancel()
        retrieveItemJob = viewModelScope.launch {
            getBitmap()
        }
    }

    private suspend fun getBitmap() {
        useCases.getAllFood.withImages(
            scope = viewModelScope
        ).collect {
            items.value = it
        }
    }
}