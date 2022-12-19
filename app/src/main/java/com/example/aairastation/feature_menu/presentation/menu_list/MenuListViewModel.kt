package com.example.aairastation.feature_menu.presentation.menu_list

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.aairastation.feature_menu.domain.MenuUseCases
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MenuListViewModel @Inject constructor(
    private val useCases: MenuUseCases
) : ViewModel() {

    private var categories = MutableStateFlow<Map<FoodCategory, List<Food>>>(mapOf())
    private var bitmaps = MutableStateFlow<Map<Food, Flow<Bitmap?>>>(mapOf())


}