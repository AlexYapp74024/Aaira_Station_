package com.example.aairastation.data.data_source

import androidx.room.*
import com.example.aairastation.feature_menu.domain.model.Food
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Food)

    @Delete
    suspend fun delete(item: Food)

    @Query("SELECT * FROM Food WHERE id = :id")
    fun getItem(id: Int): Flow<Food?>

    @Query("SELECT * FROM Food")
    fun getAll(): Flow<List<Food>>
}