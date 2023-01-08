package com.example.aairastation.feature_menu.domain.use_case.food

import com.example.aairastation.domain.ImageRepository
import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodWithImage

class InsertFood(
    private val repository: MainRepository,
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(food: Food) {
        repository.insertFood(food)
    }

    /**
     * Also updates Image
     */
    suspend operator fun invoke(
        food: FoodWithImage,
        imageUpdated: Boolean = true,
    ) {
        food.run {
            this@InsertFood(item)
            if (imageUpdated) saveImage(imageRepository)
        }
    }
}