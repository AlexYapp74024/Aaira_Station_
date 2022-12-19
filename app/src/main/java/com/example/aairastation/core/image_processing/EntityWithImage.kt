package com.example.aairastation.core.image_processing

import android.graphics.Bitmap
import com.example.aairastation.domain.ImageRepository
import kotlinx.coroutines.flow.Flow

abstract class EntityWithImage {
    abstract val imagePath: String
    abstract val bitmap: Bitmap?

    suspend fun loadImage(
        imageRepository: ImageRepository
    ): Flow<Bitmap?> {
        return imageRepository.loadImage(imagePath)
    }

    suspend fun saveImage(imageRepository: ImageRepository) {
        bitmap?.let {
            imageRepository.saveImage(imagePath, it)
        }
    }
}