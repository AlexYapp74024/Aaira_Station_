package com.example.aairastation.core.ui_util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageView

/**
 * An activity launcher that lets the user choose an image from gallery
 * The activity can be launched by calling the launch() function
 *
 * By default launch with the parameter CropImageContractOptions(null, CropImageOptions())
 * for more detail on launch options, refer to [com.canhub.cropper.CropImageOptions]
 *
 * @param useBitmap A callback that determines how the bitmap retrieved will be used
 */
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