package com.example.aairastation.feature_menu.domain.model

import android.graphics.Bitmap
import com.example.forage.core.image_processing.ImageRepository

data class FoodWithImage(
    val item: Food,
    val bitmap: Bitmap? = null
) {
    suspend fun loadImage(
        imageRepository: ImageRepository,
        onImageReceived: (Bitmap?) -> Unit
    ) {
        imageRepository.loadImage(imagePath) { bitmap ->
            onImageReceived(bitmap)
        }
    }

    suspend fun saveImage(imageRepository: ImageRepository) {
        bitmap?.let {
            imageRepository.saveImage(imagePath, it)
        }
    }
}

val FoodWithImage.imagePath
    get() = "${item.id}.png"