package com.example.aairastation.feature_menu.domain.use_case

import com.example.aairastation.domain.ImageRepository
import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodWithImage
import kotlinx.coroutines.flow.map

class GetFood(
    private val repository: MainRepository,
    private val imageRepository: ImageRepository
) {
    operator fun invoke(id: Int) = repository.getFood(id)

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