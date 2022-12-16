package com.example.aairastation.domain

import com.example.aairastation.feature_menu.domain.model.Food
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    suspend fun insert(item: Food)
    suspend fun delete(item: Food)
    fun getItem(id: Int): Flow<Food?>
    fun getAll(): Flow<List<Food>>
}