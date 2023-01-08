package com.example.aairastation.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.aairastation.domain.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(private val context: Context) : ImageRepository {

    /**
     * Flags to indicate that an image of certain name is being written to storage
     */
    private val imageBeingWritten = mutableSetOf<String>()

    override suspend fun saveImage(name: String, bitmap: Bitmap): Boolean {
        return withContext(Dispatchers.IO) {
            val file = File("${context.filesDir.absolutePath}/$name")
            if (!file.exists()) file.createNewFile()

            context.openFileOutput(name, Context.MODE_PRIVATE).use { stream ->
                imageBeingWritten.add(name)
                bitmap.compress(Bitmap.CompressFormat.PNG, 95, stream)
                imageBeingWritten.remove(name)
            }
        }
    }

    override suspend fun loadImage(name: String) = flow {
        
        waitForSavingImage(name)

        val file = File("${context.filesDir.absolutePath}/$name")
        if (!file.exists()) {
            emit(null)
            return@flow
        }

        val imageUri: Uri = Uri.fromFile(file)

        context.contentResolver.openInputStream(imageUri).use { stream ->
            emit(BitmapFactory.decodeStream(stream))
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun waitForSavingImage(name: String) {
        while (imageBeingWritten.contains(name))
            delay(100)
    }
}