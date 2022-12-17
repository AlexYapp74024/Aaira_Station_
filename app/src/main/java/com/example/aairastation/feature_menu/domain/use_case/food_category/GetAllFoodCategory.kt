package com.example.aairastation.feature_menu.domain.use_case.food_category

import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import kotlinx.coroutines.flow.Flow

class GetAllFoodCategory(
    private val repository: MainRepository,
) {
    operator fun invoke(): Flow<List<FoodCategory>> {
        return repository.getAllFoodCategory()
    }
}