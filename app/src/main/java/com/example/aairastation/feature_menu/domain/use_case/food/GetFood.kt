package com.example.aairastation.feature_menu.domain.use_case.food

import com.example.aairastation.domain.ImageRepository
import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodWithImage
import kotlinx.coroutines.flow.map

class GetFood(
    private val repository: MainRepository,
    private val imageRepository: ImageRepository
) {
    operator fun invoke(id: Long) = repository.getFood(id)

    /**
     * Assigns image, if it exists
     */
    suspend fun withImage(
        id: Long,
        onImageUpdate: (FoodWithImage) -> Unit,
    ) {
        /** Calls the above function */
        this(id)
            /** Assigns default value if null */
            .map { it ?: Food() }
            /** Convert it to image item */
            .map { FoodWithImage(it) }
            .collect { imageItem ->
                onImageUpdate(imageItem)
                /** Load image from storage */
                imageItem.loadImage(imageRepository).collect { bitmap ->
                    onImageUpdate(imageItem.copy(bitmap = bitmap))
                }
            }
    }
}