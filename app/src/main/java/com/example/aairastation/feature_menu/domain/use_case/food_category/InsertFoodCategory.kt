package com.example.aairastation.feature_menu.domain.use_case.food_category

import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_menu.domain.model.FoodCategory

class InsertFoodCategory(
    private val repository: MainRepository,
) {
    suspend operator fun invoke(category: FoodCategory) {
        repository.insertFoodCategory(category)
    }
}