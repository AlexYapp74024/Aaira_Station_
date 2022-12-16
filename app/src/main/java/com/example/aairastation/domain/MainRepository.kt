package com.example.aairastation.domain

import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.example.aairastation.feature_menu.domain.model.relations.FoodCategoryWithFood
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.NumberedTable
import com.example.aairastation.feature_order.domain.model.OrderDetail
import com.example.aairastation.feature_order.domain.model.relations.FoodWithOrderDetails
import com.example.aairastation.feature_order.domain.model.relations.OrderWithOrderDetail
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    // Foo
    suspend fun insertFood(item: Food)
    fun getFood(id: Int): Flow<Food?>
    fun getAllFood(): Flow<List<Food>>

    // Food Category
    suspend fun insertFoodCategory(item: FoodCategory)
    suspend fun deleteFoodCategory(item: FoodCategory)
    fun getAllFoodCategory(): Flow<List<FoodCategory>>

    // Order
    suspend fun insertOrder(item: FoodOrder)
    fun getOrder(id: Int): Flow<FoodOrder?>
    fun getAllOrder(): Flow<List<FoodOrder>>

    // OrderDetail
    suspend fun insertOrderDetail(item: OrderDetail)
    fun getAllOrderDetail(): Flow<List<OrderDetail>>

    // Table
    suspend fun insertTable(item: NumberedTable)
    fun getAllTable(): Flow<List<NumberedTable>>

    // Relations
    suspend fun getFoodCategoryWithFood(categoryID: Int): List<FoodCategoryWithFood>
    suspend fun getFoodWithOrderDetails(foodID: Int): List<FoodWithOrderDetails>
    suspend fun getOrderWithOrderDetail(orderID: Int): List<OrderWithOrderDetail>
}