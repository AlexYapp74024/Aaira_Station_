package com.example.aairastation.core.ui_util

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.example.aairastation.R

/**
 * Takes in a bitmap or a null value. If [bitmap] null, displays a placeholder image, otherwise displays the Image
 *
 * **NOTE:** For other parameters, refer to [androidx.compose.foundation.Image]
 *
 * @param bitmap the bitmap to be displayed, can be null
 *
 */
@Composable
fun BitmapWithDefault(
    bitmap: Bitmap?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScaleIfNotNull: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
) {
    Image(
        painter = rememberAsyncImagePainter(bitmap ?: R.drawable.emptyimage),
        contentDescription = contentDescription,
        modifier = modifier,
        alignment = alignment,
        contentScale = if (bitmap == null) contentScaleIfNotNull else ContentScale.Crop,
        alpha = alpha,
        colorFilter = colorFilter,
    )
}