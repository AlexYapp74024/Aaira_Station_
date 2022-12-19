package com.example.aairastation.feature_menu.presentation.add_item

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aairastation.feature_menu.domain.MenuUseCases
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.example.aairastation.feature_menu.domain.model.FoodWithImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val useCases: MenuUseCases
) : ViewModel() {

    private var _item = MutableStateFlow(FoodWithImage(Food()))
    val item = _item.asStateFlow()

    private var _categories = MutableStateFlow<List<FoodCategory>>(listOf())
    val categories = _categories.asStateFlow()

    private var imageUpdated = false

    init {
        useCases.getAllFoodCategory().onEach {
            _categories.value = it.toMutableList().also { list ->
                list.add(FoodCategory.noCategory)
            }
        }.launchIn(viewModelScope)
    }

    fun addNewCategory(name: String) {
        viewModelScope.launch {
            useCases.insertFoodCategory(FoodCategory(name = name))
        }
    }

    fun updateItemBitmap(bitmap: Bitmap) {
        _item.value = _item.value.copy(bitmap = bitmap)
        imageUpdated = true
    }

    fun updateItemState(item: Food) {
        _item.value = _item.value.copy(item = item)
    }

    fun retrieveItem(id: Long) {
        viewModelScope.launch {
            useCases.getFood.withImage(id) {
                _item.value = it
            }
        }
    }

    fun addItem() {
        viewModelScope.launch {
            useCases.insertFood(item.value, imageUpdated)
        }
    }
}