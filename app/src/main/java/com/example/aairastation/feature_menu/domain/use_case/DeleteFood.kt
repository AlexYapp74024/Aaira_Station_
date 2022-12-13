package com.example.aairastation.feature_menu.domain.use_case

import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.repository.MenuRepository

class DeleteFood(
    private val repository: MenuRepository
) {
    suspend operator fun invoke(food: Food) {
        repository.delete(food)
    }
}