package com.example.aairastation.feature_menu.domain.use_case.food_category

import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_menu.domain.model.FoodCategory

class DeleteFoodCategory(
    private val repository: MainRepository,
) {
    suspend operator fun invoke(category: FoodCategory): Boolean {
        repository.deleteFoodCategory(category)
        return true
    }
}

//TODO Table screen
//TODO change Calculator