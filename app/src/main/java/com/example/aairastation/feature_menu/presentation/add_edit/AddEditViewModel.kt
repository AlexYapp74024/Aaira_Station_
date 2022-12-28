package com.example.aairastation.feature_menu.presentation.add_edit

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aairastation.core.formatTo2dp
import com.example.aairastation.feature_menu.domain.MenuUseCases
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.example.aairastation.feature_menu.domain.model.FoodWithImage
import com.example.aairastation.feature_menu.domain.model.priceInRinggit
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

    var priceState by mutableStateOf(item.value.item.priceInRinggit.formatTo2dp())

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

    fun addAndSetNewCategory(name: String) = viewModelScope.launch {
        val newID =
            useCases.insertFoodCategory(FoodCategory(categoryName = name))
        useCases.getFoodCategory(newID).collect { it ->
            it?.let {
                updateItemState(_item.value.item.copy(category = it))
            }
        }
    }


    fun updateItemBitmap(bitmap: Bitmap) {
        _item.value = _item.value.copy(bitmap = bitmap)
        imageUpdated = true
    }

    fun updateItemState(item: Food) {
        _item.value = _item.value.copy(item = item)
    }

    fun retrieveItem(id: Long) = viewModelScope.launch {
        useCases.getFood.withImage(id) {
            _item.value = it
            priceState = it.item.priceInRinggit.formatTo2dp()
        }
    }

    fun addItem() = viewModelScope.launch {
        val inputPrice = priceState.toDoubleOrNull()
        val price = if (inputPrice == null) {
            _item.value.item.priceInCents
        } else {
            (inputPrice * 100).toInt()
        }

        val item = item.value.copy(
            item = item.value.item.copy(priceInCents = price)
        )
        useCases.insertFood(item, imageUpdated)
    }
}