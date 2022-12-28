package com.example.aairastation.feature_menu.domain.use_case.food_category

import android.graphics.Bitmap
import com.example.aairastation.domain.ImageRepository
import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.example.aairastation.feature_menu.domain.use_case.food.GetAllFoods
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GroupAllFoodByCategory(
    private val repository: MainRepository,
    private val imageRepository: ImageRepository,
) {
    operator fun invoke(
        onlyAvailable: Boolean = true,
        showDisabled: Boolean = false,
        scope: CoroutineScope
    ): Flow<Map<FoodCategory, Map<Food, Flow<Bitmap?>>>> {
        val categoriesFlow = GetAllFoodCategory(repository)()
        val bitmapsFlow = GetAllFoods(repository, imageRepository).withImages(
            onlyAvailable, showDisabled, scope
        )

        return combine(categoriesFlow, bitmapsFlow) { categories, bitmaps ->
            categories.associateWith { category ->
                bitmaps.filter { (food, _) ->
                    food.category == category
                }
            }.filter { (_, items) ->
                // Remove categories without items
                items.isNotEmpty()
            }
        }
    }
}