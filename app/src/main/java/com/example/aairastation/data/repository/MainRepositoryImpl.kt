package com.example.aairastation.data.repository

import com.example.aairastation.data.data_source.MenuDao
import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.example.aairastation.feature_menu.domain.model.relations.FoodCategoryWithFood
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.NumberedTable
import com.example.aairastation.feature_order.domain.model.OrderDetail
import com.example.aairastation.feature_order.domain.model.relations.FoodWithOrderDetails
import com.example.aairastation.feature_order.domain.model.relations.OrderWithOrderDetail
import kotlinx.coroutines.flow.Flow

class MainRepositoryImpl(
    private val dao: MenuDao
) : MainRepository {

    override suspend fun insertFood(item: Food) = dao.insertFood(item)
    override fun getFood(id: Int): Flow<Food?> = dao.getFood(id)
    override fun getAllFood(): Flow<List<Food>> = dao.getAllFood()

    override suspend fun insertFoodCategory(item: FoodCategory) = dao.insertFoodCategory(item)
    override suspend fun deleteFoodCategory(item: FoodCategory) = dao.deleteFoodCategory(item)
    override fun getAllFoodCategory(): Flow<List<FoodCategory>> = dao.getAllFoodCategory()

    override suspend fun insertOrder(item: FoodOrder) = dao.insertOrder(item)
    override fun getOrder(id: Int): Flow<FoodOrder?> = dao.getOrder(id)
    override fun getAllOrder(): Flow<List<FoodOrder>> = dao.getAllOrder()

    override suspend fun insertOrderDetail(item: OrderDetail) = dao.insertOrderDetail(item)
    override fun getAllOrderDetail(): Flow<List<OrderDetail>> = dao.getAllOrderDetail()

    override suspend fun insertTable(item: NumberedTable) = dao.insertTable(item)
    override fun getAllTable(): Flow<List<NumberedTable>> = dao.getAllTable()

    override suspend fun getFoodCategoryWithFood(categoryID: Int): List<FoodCategoryWithFood> =
        dao.getFoodCategoryWithFood(categoryID)

    override suspend fun getFoodWithOrderDetails(foodID: Int): List<FoodWithOrderDetails> =
        dao.getFoodWithOrderDetails(foodID)

    override suspend fun getOrderWithOrderDetail(orderID: Int): List<OrderWithOrderDetail> =
        dao.getOrderWithOrderDetail(orderID)

}