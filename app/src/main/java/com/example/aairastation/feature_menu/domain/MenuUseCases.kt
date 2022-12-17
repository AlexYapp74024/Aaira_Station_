package com.example.aairastation.feature_menu.domain

import com.example.aairastation.domain.ImageRepository
import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_menu.domain.use_case.food.InsertFood
import com.example.aairastation.feature_menu.domain.use_case.food.GetAllFoods
import com.example.aairastation.feature_menu.domain.use_case.food.GetFood

data class MenuUseCases(
    val getAllFood: GetAllFoods,
    val getFood: GetFood,
    val insertFood: InsertFood,
) {
    companion object {
        fun create(
            repository: MainRepository,
            imageRepository: ImageRepository,
        ) = MenuUseCases(
            getAllFood = GetAllFoods(repository, imageRepository),
            getFood = GetFood(repository, imageRepository),
            insertFood = InsertFood(repository, imageRepository),
        )
    }
}