package com.example.forage.core.image_processing

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(private val context: Context) : ImageRepository {

    private val imageBeingWritten = mutableSetOf<String>()

    override suspend fun saveImage(name: String, bitmap: Bitmap): Boolean {

        return withContext(Dispatchers.IO) {
            context.openFileOutput(name, Context.MODE_PRIVATE).use { stream ->
                imageBeingWritten.add(name)
                bitmap.compress(Bitmap.CompressFormat.PNG, 95, stream)
                imageBeingWritten.remove(name)
            }
        }
    }

    override suspend fun loadImage(
        name: String, onImageReceived: (Bitmap?) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            val file = File("${context.filesDir.absolutePath}/$name")
            val imageUri: Uri = Uri.fromFile(file)

            while (imageBeingWritten.contains(name)) {
                delay(100)
            }

            context.contentResolver.openInputStream(imageUri).use { stream ->
                onImageReceived(BitmapFactory.decodeStream(stream))
            }
        }
    }
}