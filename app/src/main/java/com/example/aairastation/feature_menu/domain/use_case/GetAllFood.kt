package com.example.aairastation.feature_menu.domain.use_case

import android.graphics.Bitmap
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodWithImage
import com.example.aairastation.feature_menu.domain.repository.MenuRepository
import com.example.forage.core.image_processing.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllFoods(
    private val repository: MenuRepository,
    private val imageRepository: ImageRepository
) {
    operator fun invoke(
        onlyInSeason: Boolean = true,
    ): Flow<List<Food>> {
        return repository.getAll().map { items ->
            items.filter { !onlyInSeason || it.available }
        }
    }

    suspend fun withImages(
        onlyInSeason: Boolean = true,
        onImageUpdate: (Food, Bitmap?) -> Unit,
    ) {
        this(onlyInSeason).collect { items ->
            items.forEach { item ->
                onImageUpdate(item, null)
                FoodWithImage(item).loadImage(imageRepository) { bmp ->
                    onImageUpdate(item, bmp)
                }
            }
        }
    }
}