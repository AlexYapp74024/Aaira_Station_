package com.example.aairastation.feature_menu.domain.use_case.food_category

import com.example.aairastation.domain.MainRepository

class GetFoodCategory(
    private val repository: MainRepository
) {
    operator fun invoke(id: Long) = repository.getFoodCategory(id)
}