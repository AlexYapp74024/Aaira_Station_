package com.example.forage.core.ui_util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageView

@Composable
fun getImageFromInternalStorageLauncher(useBitmap: (Bitmap) -> Unit)
        : ManagedActivityResultLauncher<CropImageContractOptions, CropImageView.CropResult> {
    val context = LocalContext.current

    return rememberLauncherForActivityResult(
        CropImageContract()
    ) { result ->
        result.uriContent?.let {
            context.contentResolver.openInputStream(it).use { stream ->
                useBitmap(BitmapFactory.decodeStream(stream))
            }
        }
    }
}