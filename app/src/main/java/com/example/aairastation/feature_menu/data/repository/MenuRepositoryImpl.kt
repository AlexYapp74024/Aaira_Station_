package com.example.aairastation.feature_menu.data.repository

import com.example.aairastation.feature_menu.data.data_source.MenuDao
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.repository.MenuRepository
import kotlinx.coroutines.flow.Flow

class MenuRepositoryImpl(
    private val dao: MenuDao
) : MenuRepository {
    override suspend fun insert(item: Food) {
        dao.insert(item)
    }

    override suspend fun delete(item: Food) {
        dao.delete(item)
    }

    override fun getItem(id: Int): Flow<Food?> = dao.getItem(id)
    override fun getAll(): Flow<List<Food>> = dao.getAll()
}