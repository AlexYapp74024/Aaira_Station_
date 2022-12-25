package com.example.aairastation.data.repository

import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.NumberedTable
import com.example.aairastation.feature_order.domain.model.OrderDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TestRepository : MainRepository {

    private val foods = mutableListOf<Food>()
    private val categories = mutableListOf<FoodCategory>()
    private val orders = mutableListOf<FoodOrder>()
    private val orderDetails = mutableListOf<OrderDetail>()
    private val tables = mutableListOf<NumberedTable>()

    override suspend fun insertFood(item: Food): Long {
        categories.add(item.category)
        foods.add(item)
        return item.foodId
    }

    override fun getFood(id: Long): Flow<Food?> {
        return flowOf(foods.find { it.foodId == id })
    }

    override fun getAllFood(): Flow<List<Food>> {
        return flowOf(foods)
    }

    override suspend fun insertFoodCategory(item: FoodCategory): Long {
        categories.add(item)
        return item.categoryId
    }

    override suspend fun deleteFoodCategory(item: FoodCategory) {
        categories.remove(item)
    }

    override fun getAllFoodCategory(): Flow<List<FoodCategory>> {
        return flowOf(categories)
    }

    override suspend fun insertOrder(item: FoodOrder): Long {
        orders.add(item)
        return item.orderId
    }

    override fun getOrder(id: Long): Flow<FoodOrder?> {
        return flowOf(orders.find { it.orderId == id })
    }

    override fun getAllOrder(): Flow<List<FoodOrder>> {
        return flowOf(orders)
    }

    override suspend fun insertOrderDetail(item: OrderDetail): Long {
        orderDetails.add(item)
        return item.detailId
    }

    override fun getAllOrderDetail(): Flow<List<OrderDetail>> {
        return flowOf(orderDetails)
    }

    override suspend fun insertTable(item: NumberedTable): Long {
        tables.add(item)
        return item.tableId
    }

    override suspend fun deleteTable(item: NumberedTable) {
        tables.remove(item)
    }

    override fun getAllTable(): Flow<List<NumberedTable>> {
        return flowOf(tables)
    }
}