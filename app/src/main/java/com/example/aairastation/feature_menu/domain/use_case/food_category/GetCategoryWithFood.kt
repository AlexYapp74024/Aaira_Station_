package com.example.aairastation.feature_menu.domain.use_case.food_category

import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory

class GetCategoryWithFood(
    private val repository: MainRepository
) {
    suspend operator fun invoke(categoryID: Long) =
        mutableMapOf<FoodCategory, List<Food>>().also { map ->
            repository.getFoodCategoryWithFood(categoryID).map { (category, foods) ->
                map[category] = foods
            }
        }

    suspend operator fun invoke() = mutableMapOf<FoodCategory, List<Food>>().also { map ->
        repository.getFoodCategoryWithFood().map { (category, foods) ->
            map[category] = foods
        }
    }
}