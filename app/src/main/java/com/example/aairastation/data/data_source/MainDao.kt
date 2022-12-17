package com.example.aairastation.data.data_source

import androidx.room.*
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.example.aairastation.feature_menu.domain.model.relations.FoodCategoryWithFood
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.NumberedTable
import com.example.aairastation.feature_order.domain.model.OrderDetail
import com.example.aairastation.feature_order.domain.model.relations.FoodWithOrderDetails
import com.example.aairastation.feature_order.domain.model.relations.OrderWithOrderDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface MainDao {
    // Food
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(item: Food)

    @Query("SELECT * FROM Food WHERE id = :id")
    fun getFood(id: Int): Flow<Food?>

    @Query("SELECT * FROM Food")
    fun getAllFood(): Flow<List<Food>>

    // Food Category
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodCategory(item: FoodCategory)

    @Delete
    suspend fun deleteFoodCategory(item: FoodCategory)

    @Query("SELECT * FROM FoodCategory")
    fun getAllFoodCategory(): Flow<List<FoodCategory>>

    // Order
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(item: FoodOrder)

    @Query("SELECT * FROM FoodOrder WHERE id = :id")
    fun getOrder(id: Int): Flow<FoodOrder?>

    @Query("SELECT * FROM FoodOrder")
    fun getAllOrder(): Flow<List<FoodOrder>>

    // OrderDetail
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderDetail(item: OrderDetail)

    @Query("SELECT * FROM OrderDetail")
    fun getAllOrderDetail(): Flow<List<OrderDetail>>

    // Table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTable(item: NumberedTable)

    @Query("SELECT * FROM NumberedTable")
    fun getAllTable(): Flow<List<NumberedTable>>

    // Relations

    @Transaction
    @Query("SELECT * FROM FoodCategory WHERE id = :categoryID")
    suspend fun getFoodCategoryWithFood(categoryID: Int): List<FoodCategoryWithFood>

    @Transaction
    @Query("SELECT * FROM Food WHERE id = :foodID")
    suspend fun getFoodWithOrderDetails(foodID: Int): List<FoodWithOrderDetails>

    @Transaction
    @Query("SELECT * FROM OrderDetail WHERE id = :orderID")
    suspend fun getOrderWithOrderDetail(orderID: Int): List<OrderWithOrderDetail>
}