package com.example.aairastation.feature_menu.domain.use_case

import com.example.aairastation.domain.ImageRepository
import com.example.aairastation.domain.MenuRepository
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodWithImage

class AddFood(
    private val repository: MenuRepository,
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(food: Food) {
        repository.insert(food)
    }

    suspend operator fun invoke(food: FoodWithImage) {
        food.run {
            this@AddFood(item)
            saveImage(imageRepository)
        }
    }
}