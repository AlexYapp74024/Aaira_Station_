package com.example.aairastation.feature_menu.domain.use_case.food

import android.graphics.Bitmap
import com.example.aairastation.domain.ImageRepository
import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodWithImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllFoods(
    private val repository: MainRepository,
    private val imageRepository: ImageRepository,
) {
    operator fun invoke(
        onlyAvailable: Boolean = true,
        showDisabled: Boolean = false,
    ): Flow<List<Food>> {
        return repository.getAllFood().map { items ->
            items.filter { !onlyAvailable || it.available }
                .filter { showDisabled || !it.disabled }
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