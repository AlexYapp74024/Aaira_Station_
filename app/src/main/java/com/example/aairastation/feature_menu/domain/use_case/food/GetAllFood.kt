package com.example.aairastation.feature_menu.domain.use_case.food

import android.graphics.Bitmap
import com.example.aairastation.domain.ImageRepository
import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodWithImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn

class GetAllFoods(
    private val repository: MainRepository,
    private val imageRepository: ImageRepository,
) {
    operator fun invoke(
        onlyAvailable: Boolean = true,
        showDisabled: Boolean = false,
    ): Flow<List<Food>> {
        return repository.getAllFood().map { items ->
            items.filter { !onlyAvailable || it.available }
                .filter { showDisabled || !it.foodDisabled }
        }
    }

    suspend fun withImages(
        onlyAvailable: Boolean = true,
        showDisabled: Boolean = false,
        scope: CoroutineScope
    ): Flow<Map<Food, Flow<Bitmap?>>> {
        return this(
            onlyAvailable = onlyAvailable,
            showDisabled = showDisabled,
        ).map { items ->
            mutableMapOf<Food, Flow<Bitmap?>>().also { map ->
                items.map { item ->
                    FoodWithImage(item)
                }.map {
                    map[it.item] = it.loadImage(imageRepository).shareIn(
                        scope = scope,
                        started = SharingStarted.Lazily,
                        replay = 1
                    )
                }
            }
        }
    }
}