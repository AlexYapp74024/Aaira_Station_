package com.example.aairastation.domain

import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.NumberedTable
import com.example.aairastation.feature_order.domain.model.OrderDetail
import kotlinx.coroutines.flow.Flow

/**
 * All insertion returns the item's id after insertion
 * this is helpful when inserting new items
 */
interface MainRepository {

    /** Food */
    suspend fun insertFood(item: Food): Long
    fun getFood(id: Long): Flow<Food?>
    fun getAllFood(): Flow<List<Food>>

    /** Food Category */
    suspend fun insertFoodCategory(item: FoodCategory): Long
    suspend fun deleteFoodCategory(item: FoodCategory)
    fun getFoodCategory(id: Long): Flow<FoodCategory?>
    fun getAllFoodCategory(): Flow<List<FoodCategory>>

    /** Order */
    suspend fun insertOrder(item: FoodOrder): Long
    fun getOrder(id: Long): Flow<FoodOrder?>
    fun getAllOrder(): Flow<List<FoodOrder>>

    /** OrderDetail */
    suspend fun insertOrderDetail(item: OrderDetail): Long
    fun getAllOrderDetail(): Flow<List<OrderDetail>>

    /** Table */
    suspend fun insertTable(item: NumberedTable): Long
    suspend fun deleteTable(item: NumberedTable)
    fun getTable(id: Long): Flow<NumberedTable?>
    fun getAllTable(): Flow<List<NumberedTable>>
}