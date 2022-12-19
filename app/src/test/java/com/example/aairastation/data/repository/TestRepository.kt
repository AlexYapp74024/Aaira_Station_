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
        foods.add(item)
        return item.id
    }

    override fun getFood(id: Long): Flow<Food?> {
        return flowOf(foods.find { it.id == id })
    }

    override fun getAllFood(): Flow<List<Food>> {
        return flowOf(foods)
    }

    override suspend fun insertFoodCategory(item: FoodCategory): Long {
        categories.add(item)
        return item.id
    }

    override suspend fun deleteFoodCategory(item: FoodCategory) {
        categories.remove(item)
    }

    override fun getAllFoodCategory(): Flow<List<FoodCategory>> {
        return flowOf(categories)
    }

    override suspend fun insertOrder(item: FoodOrder): Long {
        orders.add(item)
        return item.id
    }

    override fun getOrder(id: Long): Flow<FoodOrder?> {
        return flowOf(orders.find { it.id == id })
    }

    override fun getAllOrder(): Flow<List<FoodOrder>> {
        return flowOf(orders)
    }

    override suspend fun insertOrderDetail(item: OrderDetail): Long {
        orderDetails.add(item)
        return item.id
    }

    override fun getAllOrderDetail(): Flow<List<OrderDetail>> {
        return flowOf(orderDetails)
    }

    override suspend fun insertTable(item: NumberedTable): Long {
        tables.add(item)
        return item.id
    }

    override suspend fun deleteTable(item: NumberedTable) {
        tables.remove(item)
    }

    override fun getAllTable(): Flow<List<NumberedTable>> {
        return flowOf(tables)
    }

    override suspend fun getFoodCategoryWithFood(category: FoodCategory): Map<FoodCategory, List<Food>> {
        val items = foods.filter { it.categoryID == category.id }
        return mapOf(category to items)
    }

    override suspend fun getFoodCategoryWithFood(): Map<FoodCategory, List<Food>> {
        return categories.associateWith { category ->
            foods.filter { it.categoryID == category.id }
        }
    }

    override suspend fun getFoodWithOrderDetail(food: Food): Map<Food, List<OrderDetail>> {
        val items = orderDetails.filter { it.foodID == food.id }
        return mapOf(food to items)
    }

    override suspend fun getFoodWithOrderDetails(): Map<Food, List<OrderDetail>> {
        return foods.associateWith { food ->
            orderDetails.filter { it.foodID == food.id }
        }
    }

    override suspend fun getOrderWithOrderDetail(order: FoodOrder): Map<FoodOrder, List<OrderDetail>> {
        val items = orderDetails.filter { it.orderID == order.id }
        return mapOf(order to items)
    }

    override suspend fun getOrderWithOrderDetail(): Map<FoodOrder, List<OrderDetail>> {
        return orders.associateWith { order ->
            orderDetails.filter { it.orderID == order.id }
        }
    }

}