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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuListViewModel @Inject constructor(
    private val useCases: MenuUseCases
) : ViewModel() {

    private var categories = MutableStateFlow<Map<FoodCategory, List<Food>>>(mapOf())
    private var bitmaps = MutableStateFlow<Map<Food, Flow<Bitmap?>>>(mapOf())

    var itemsAndCategories = combine(categories, bitmaps) { categories, bitmaps ->
        categories.map { (category, items) ->
            val itemAndBitmap = items.associateWith {
                bitmaps[it] ?: flowOf(null)
            }
            category to itemAndBitmap
        }.toMap().toMutableMap().also { map ->
            val itemWithNullCategory = bitmaps.filter { (item, _) -> item.categoryID == null }
            map[FoodCategory.noCategory] = itemWithNullCategory
        }.filter { (_, items) ->
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
        categories.value = useCases.getCategoryWithFood()
    }

    private suspend fun getBitmap() {
        useCases.getAllFood.withImages(
            scope = viewModelScope
        ).collect {
            bitmaps.value = it
        }
    }
}