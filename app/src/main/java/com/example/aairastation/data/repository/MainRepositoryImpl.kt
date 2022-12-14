package com.example.aairastation.data.repository

import com.example.aairastation.data.data_source.MainDao
import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.NumberedTable
import com.example.aairastation.feature_order.domain.model.OrderDetail
import kotlinx.coroutines.flow.Flow

/**
 * See the base interface for more info in [MainRepository.kt]
 */
class MainRepositoryImpl(
    private val dao: MainDao
) : MainRepository {

    override suspend fun insertFood(item: Food): Long = dao.insertFood(item)
    override fun getFood(id: Long): Flow<Food?> = dao.getFood(id)
    override fun getAllFood(): Flow<List<Food>> = dao.getAllFood()

    override suspend fun insertFoodCategory(item: FoodCategory): Long = dao.insertFoodCategory(item)
    override suspend fun deleteFoodCategory(item: FoodCategory) = dao.deleteFoodCategory(item)
    override fun getFoodCategory(id: Long): Flow<FoodCategory?> = dao.getFoodCategory(id)
    override fun getAllFoodCategory(): Flow<List<FoodCategory>> = dao.getAllFoodCategory()

    override suspend fun insertOrder(item: FoodOrder): Long = dao.insertOrder(item)
    override fun getOrder(id: Long): Flow<FoodOrder?> = dao.getOrder(id)
    override fun getAllOrder(): Flow<List<FoodOrder>> = dao.getAllOrder()

    override suspend fun insertOrderDetail(item: OrderDetail): Long = dao.insertOrderDetail(item)
    override fun getAllOrderDetail(): Flow<List<OrderDetail>> = dao.getAllOrderDetail()

    override suspend fun insertTable(item: NumberedTable): Long = dao.insertTable(item)
    override suspend fun deleteTable(item: NumberedTable) = dao.deleteTable(item)
    override fun getTable(id: Long): Flow<NumberedTable?> = dao.getTable(id)
    override fun getAllTable(): Flow<List<NumberedTable>> = dao.getAllTable()
}