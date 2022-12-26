package com.example.aairastation.feature_settings.domain.use_cases

import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.NumberedTable
import com.example.aairastation.feature_order.domain.model.OrderDetail
import kotlinx.coroutines.flow.first

/**
 * Generates Preloaded Data
 * Is Solely designed for the purpose of testing
 */
class LoadPreloadedData(
    private val repository: MainRepository
) {
    suspend operator fun invoke() {
        if (repository.getAllFoodCategory().first().isEmpty())
            category.forEach { (_, item) ->
                repository.insertFoodCategory(item)
            }

        if (repository.getAllFood().first().isEmpty())
            food.forEach { (_, item) ->
                repository.insertFood(item)
            }

        if (repository.getAllOrder().first().isEmpty())
            order.forEach { (_, item) ->
                repository.insertOrder(item)
            }

        if (repository.getAllOrderDetail().first().isEmpty())
            detail.forEach { (_, item) ->
                repository.insertOrderDetail(item)
            }

        if (repository.getAllTable().first().isEmpty())
            table.forEach { (_, item) ->
                repository.insertTable(item)
            }
    }

    private val category = mapOf(
        1 to FoodCategory(1, "Rice"),
        2 to FoodCategory(2, "Noodles"),
        3 to FoodCategory(3, "Drink"),
        4 to FoodCategory(4, "Add-Ons"),
    )
    private val food = mapOf(
        1 to Food(
            foodId = 1,
            foodName = "Nasi Lemak",
            category = category[1]!!,
            priceInCents = 800,
            available = true,
            description = "Sedap"
        ),
        2 to Food(
            foodId = 2,
            foodName = "Nasi Goreng",
            category = category[1]!!,
            priceInCents = 600,
            available = true,
            description = "Sedap"
        ),
        3 to Food(
            foodId = 3,
            foodName = "Nasi Ayam",
            category = category[1]!!,
            priceInCents = 900,
            available = true,
            description = "Sedap"
        ),
        4 to Food(
            foodId = 4,
            foodName = "Mee Goreng",
            category = category[2]!!,
            priceInCents = 500,
            available = true,
            description = "Sedap"
        ),
        5 to Food(
            foodId = 5,
            foodName = "Mee Sup",
            category = category[2]!!,
            priceInCents = 600,
            available = true,
            description = "Sedap"
        ),
        6 to Food(
            foodId = 6,
            foodName = "Milo",
            category = category[3]!!,
            priceInCents = 300,
            available = true,
            description = "Sedap"
        ),
        7 to Food(
            foodId = 7,
            foodName = "NesCafe",
            category = category[3]!!,
            priceInCents = 300,
            available = true,
            description = "Sedap"
        ),
        8 to Food(
            foodId = 8,
            foodName = "Hot Dog",
            category = category[4]!!,
            priceInCents = 150,
            available = true,
            description = "Sedap"
        ),
        9 to Food(
            foodId = 9,
            foodName = "Egg",
            category = category[4]!!,
            priceInCents = 100,
            available = true,
            description = "Sedap"
        ),
    )
    private val table = mapOf(
        1 to NumberedTable(1, 1),
        2 to NumberedTable(2, 2),
        3 to NumberedTable(3, 3),
    )
    private val order = mapOf(
        1 to FoodOrder(1, table[1]),
        2 to FoodOrder(2, table[1]),
        3 to FoodOrder(3, table[2]),
    )
    private val detail = mapOf(
        1 to OrderDetail(1, order[1]!!, food[1]!!, 1, false),
        2 to OrderDetail(2, order[1]!!, food[2]!!, 1, false),
        3 to OrderDetail(3, order[2]!!, food[3]!!, 1, true),
        4 to OrderDetail(4, order[2]!!, food[4]!!, 1, false),
        5 to OrderDetail(5, order[2]!!, food[5]!!, 1, true),
        6 to OrderDetail(6, order[3]!!, food[6]!!, 1, true),
        7 to OrderDetail(7, order[3]!!, food[7]!!, 1, true),
        8 to OrderDetail(8, order[3]!!, food[8]!!, 1, true),
        9 to OrderDetail(9, order[3]!!, food[9]!!, 1, true),
    )
}