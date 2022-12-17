package com.example.aairastation.domain

import android.graphics.Bitmap

interface ImageRepository {
    suspend fun saveImage(name: String, bitmap: Bitmap): Boolean
    suspend fun loadImage(name: String, onImageReceived: (Bitmap?) -> Unit)
}