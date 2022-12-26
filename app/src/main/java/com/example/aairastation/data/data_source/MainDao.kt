package com.example.aairastation.data.data_source

import androidx.room.*
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.NumberedTable
import com.example.aairastation.feature_order.domain.model.OrderDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface MainDao {
    // Food
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(item: Food): Long

    @Query("SELECT * FROM Food WHERE foodId = :id")
    fun getFood(id: Long): Flow<Food?>

    @Query("SELECT * FROM Food")
    fun getAllFood(): Flow<List<Food>>

    // Food Category
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodCategory(item: FoodCategory): Long

    @Delete
    suspend fun deleteFoodCategory(item: FoodCategory)

    @Query("SELECT * FROM FoodCategory WHERE categoryId = :id")
    fun getFoodCategory(id: Long): Flow<FoodCategory?>

    @Query("SELECT * FROM FoodCategory")
    fun getAllFoodCategory(): Flow<List<FoodCategory>>

    // Order
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(item: FoodOrder): Long

    @Query("SELECT * FROM FoodOrder WHERE orderId = :id")
    fun getOrder(id: Long): Flow<FoodOrder?>

    @Query("SELECT * FROM FoodOrder")
    fun getAllOrder(): Flow<List<FoodOrder>>

    // OrderDetail
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderDetail(item: OrderDetail): Long

    @Query("SELECT * FROM OrderDetail")
    fun getAllOrderDetail(): Flow<List<OrderDetail>>

    // Table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTable(item: NumberedTable): Long

    @Query("SELECT * FROM NumberedTable WHERE tableId = :id")
    fun getTable(id: Long): Flow<NumberedTable?>

    @Delete
    suspend fun deleteTable(item: NumberedTable)

    @Query("SELECT * FROM NumberedTable")
    fun getAllTable(): Flow<List<NumberedTable>>
}