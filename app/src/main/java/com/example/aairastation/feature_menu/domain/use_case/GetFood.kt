package com.example.aairastation.feature_menu.domain.use_case

import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodWithImage
import com.example.aairastation.feature_menu.domain.repository.MenuRepository
import com.example.forage.core.image_processing.ImageRepository
import kotlinx.coroutines.flow.map

class GetFood(
    private val repository: MenuRepository,
    private val imageRepository: ImageRepository
) {
    operator fun invoke(id: Int) = repository.getItem(id)

    suspend fun withImage(
        id: Int,
        onImageUpdate: (FoodWithImage) -> Unit,
    ) {
        this(id).map { it ?: Food() }
            .map { FoodWithImage(it) }
            .collect { imageItem ->
                onImageUpdate(imageItem)
                imageItem.loadImage(imageRepository) { bitmap ->
                    onImageUpdate(imageItem.copy(bitmap = bitmap))
                }
            }
    }
}