package com.example.aairastation.feature_menu.domain

import com.example.aairastation.domain.ImageRepository
import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_menu.domain.use_case.food.GetAllFoods
import com.example.aairastation.feature_menu.domain.use_case.food.GetFood
import com.example.aairastation.feature_menu.domain.use_case.food.InsertFood
import com.example.aairastation.feature_menu.domain.use_case.food_category.DeleteFoodCategory
import com.example.aairastation.feature_menu.domain.use_case.food_category.GetAllFoodCategory
import com.example.aairastation.feature_menu.domain.use_case.food_category.GroupAllFoodByCategory
import com.example.aairastation.feature_menu.domain.use_case.food_category.InsertFoodCategory

data class MenuUseCases(
    val getAllFood: GetAllFoods,
    val getFood: GetFood,
    val insertFood: InsertFood,

    val getAllFoodCategory: GetAllFoodCategory,
    val insertFoodCategory: InsertFoodCategory,
    val deleteFoodCategory: DeleteFoodCategory,
    val groupAllFoodByCategory: GroupAllFoodByCategory,
) {
    companion object {
        fun create(
            repository: MainRepository,
            imageRepository: ImageRepository,
        ) = MenuUseCases(
            getAllFood = GetAllFoods(repository, imageRepository),
            getFood = GetFood(repository, imageRepository),
            insertFood = InsertFood(repository, imageRepository),

            getAllFoodCategory = GetAllFoodCategory(repository),
            insertFoodCategory = InsertFoodCategory(repository),
            deleteFoodCategory = DeleteFoodCategory(repository),
            groupAllFoodByCategory = GroupAllFoodByCategory(repository, imageRepository),
        )
    }
}