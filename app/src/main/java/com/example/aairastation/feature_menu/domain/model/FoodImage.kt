package com.example.aairastation.feature_menu.domain.model

import android.graphics.Bitmap
import com.example.aairastation.core.image_processing.EntityWithImage

data class FoodWithImage(
    val item: Food,
    override val bitmap: Bitmap? = null
) : EntityWithImage() {

    override val imagePath
        get() = "${item.id}.png"
}

