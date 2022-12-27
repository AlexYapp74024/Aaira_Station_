package com.example.aairastation.feature_settings.domain.use_cases

import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.NumberedTable
import com.example.aairastation.feature_order.domain.model.OrderDetail
import kotlinx.coroutines.flow.first

class LoadFullPreloadedData(
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
            foodName = " Milo O",
            category = category[3]!!,
            priceInCents = 450,
            available = true,
            description = "Sedap"
        ),
        10 to Food(
            foodId = 10 ,
            foodName = " Milo Tarik",
            category = category[3]!!,
            priceInCents = 450,
            available = true,
            description = "Sedap"
        ),
        11 to Food(
            foodId = 11 ,
            foodName = "Milo Tabur",
            category = category[3]!!,
            priceInCents = 400,
            available = true,
            description = "Sedap"
        ),
        12 to Food(
            foodId = 12 ,
            foodName = " Milo Dinasour",
            category = category[3]!!,
            priceInCents = 500 ,
            available = true,
            description = "Sedap"
        ),
        13 to Food(
            foodId = 13 ,
            foodName = " Nescafé O",
            category = category[3]!!,
            priceInCents = 350,
            available = true,
            description = "Sedap"
        ),
        14 to Food(
            foodId = 14 ,
            foodName = " Nescafé Tarik",
            category = category[3]!!,
            priceInCents = 350,
            available = true,
            description = "Sedap"
        ),
        15 to Food(
            foodId = 15 ,
            foodName = " Nescafé Susu",
            category = category[3]!!,
            priceInCents = 350,
            available = true,
            description = "Sedap"
        ),
        16 to Food(
            foodId =16 ,
            foodName = " Neslo ",
            category = category[3]!!,
            priceInCents = 400,
            available = true,
            description = "Sedap"
        ),
        17 to Food(
            foodId = 17 ,
            foodName = " Neslo Tarik",
            category = category[3]!!,
            priceInCents = 450,
            available = true,
            description = "Sedap"
        ),
        18 to Food(
            foodId = 18 ,
            foodName = " Horlickh ",
            category = category[3]!!,
            priceInCents = 600,
            available = true,
            description = "Sedap"
        ),
        19 to Food(
            foodId = 19 ,
            foodName = " Horlickh O",
            category = category[3]!!,
            priceInCents = 650,
            available = true,
            description = "Sedap"
        ),
        20 to Food(
            foodId = 20 ,
            foodName = " Horlickh Tarik",
            category = category[3]!!,
            priceInCents = 700,
            available = true,
            description = "Sedap"
        ),
        21 to Food(
            foodId = 21 ,
            foodName = " Jus Apple",
            category = category[3]!!,
            priceInCents = 600,
            available = true,
            description = "Sedap"
        ),
        22 to Food(
            foodId = 22 ,
            foodName = " Jus Oren",
            category = category[3]!!,
            priceInCents = 550,
            available = true,
            description = "Sedap"
        ),
        23 to Food(
            foodId = 23 ,
            foodName = " Jus Carrot",
            category = category[3]!!,
            priceInCents = 600,
            available = true,
            description = "Sedap"
        ),
        24 to Food(
            foodId = 24 ,
            foodName = " Jus Manggo",
            category = category[3]!!,
            priceInCents = 650,
            available = true,
            description = "Sedap"
        ),
        26 to Food(
            foodId = 26 ,
            foodName = " Jus Watermelon",
            category = category[3]!!,
            priceInCents = 600,
            available = true,
            description = "Sedap"
        ),
        27 to Food(
            foodId = 27 ,
            foodName = " Jus Dragonfruit",
            category = category[3]!!,
            priceInCents = 500,
            available = true,
            description = "Sedap"
        ),
        28 to Food(
            foodId = 28 ,
            foodName = " Chocolate ",
            category = category[3]!!,
            priceInCents = 400 ,
            available = true,
            description = "Sedap"
        ),
        29 to Food(
            foodId = 29 ,
            foodName = " Moccacino ",
            category = category[3]!!,
            priceInCents = 450 ,
            available = true,
            description = "Sedap"
        ),
        30 to Food(
            foodId = 30 ,
            foodName = " Bubble gum",
            category = category[3]!!,
            priceInCents = 450 ,
            available = true,
            description = "Sedap"
        ),
        31 to Food(
            foodId = 31 ,
            foodName = " Vanilla blue",
            category = category[3]!!,
            priceInCents = 450 ,
            available = true,
            description = "Sedap"
        ),
        32 to Food(
            foodId = 32 ,
            foodName = " Grape ",
            category = category[3]!!,
            priceInCents = 550 ,
            available = true,
            description = "Sedap"
        ),
        33 to Food(
            foodId = 33 ,
            foodName = " Guava ",
            category = category[3]!!,
            priceInCents = 500 ,
            available = true,
            description = "Sedap"
        ),
        34 to Food(
            foodId = 34 ,
            foodName = " Durian ",
            category = category[3]!!,
            priceInCents = 500 ,
            available = true,
            description = "Sedap"
        ),
        35 to Food(
            foodId = 35 ,
            foodName = " Manggo ",
            category = category[3]!!,
            priceInCents = 500 ,
            available = true,
            description = "Sedap"
        ),
        36 to Food(
            foodId = 36 ,
            foodName = " Honeydew ",
            category = category[3]!!,
            priceInCents = 500 ,
            available = true,
            description = "Sedap"
        ),
        37 to Food(
            foodId = 37 ,
            foodName = " Avocado ",
            category = category[3]!!,
            priceInCents = 500 ,
            available = true,
            description = "Sedap"
        ),
        38 to Food(
            foodId = 38 ,
            foodName = " Yogurt strawberry",
            category = category[3]!!,
            priceInCents = 600 ,
            available = true,
            description = "Sedap"
        ),
        39 to Food(
            foodId = 39 ,
            foodName = " Cola ",
            category = category[3]!!,
            priceInCents = 200 ,
            available = true,
            description = "Sedap"
        ),
        40 to Food(
            foodId = 40 ,
            foodName = " Pepsi ",
            category = category[3]!!,
            priceInCents = 200 ,
            available = true,
            description = "Sedap"
        ),
        41 to Food(
            foodId = 41 ,
            foodName = " Sprite ",
            category = category[3]!!,
            priceInCents = 200 ,
            available = true,
            description = "Sedap"
        ),
        42 to Food(
            foodId = 42 ,
            foodName = " 100plus ",
            category = category[3]!!,
            priceInCents = 250 ,
            available = true,
            description = "Sedap"
        ),
        43 to Food(
            foodId = 43 ,
            foodName = " Soda F&N",
            category = category[3]!!,
            priceInCents = 200 ,
            available = true,
            description = "Sedap"
        ),
        44 to Food(
            foodId = 44 ,
            foodName = " Sarsi ",
            category = category[3]!!,
            priceInCents = 200 ,
            available = true,
            description = "Sedap"
        ),
        45 to Food(
            foodId = 45 ,
            foodName = " Soya ",
            category = category[3]!!,
            priceInCents = 200 ,
            available = true,
            description = "Sedap"
        ),
        46 to Food(
            foodId = 46 ,
            foodName = " Cincau ",
            category = category[3]!!,
            priceInCents = 350 ,
            available = true,
            description = "Sedap"
        ),
        47 to Food(
            foodId = 47 ,
            foodName = " Teh Bunga",
            category = category[3]!!,
            priceInCents = 300 ,
            available = true,
            description = "Sedap"
        ),
        48 to Food(
            foodId = 48 ,
            foodName = " Sirap Rose",
            category = category[3]!!,
            priceInCents = 400 ,
            available = true,
            description = "Sedap"
        ),
        49 to Food(
            foodId = 49 ,
            foodName = " Sirap Oren",
            category = category[3]!!,
            priceInCents = 400 ,
            available = true,
            description = "Sedap"
        ),
        50 to Food(
            foodId = 50 ,
            foodName = " Sirap Sarsi",
            category = category[3]!!,
            priceInCents = 350 ,
            available = true,
            description = "Sedap"
        ),
        51 to Food(
            foodId = 51 ,
            foodName = " Sunquick oren",
            category = category[3]!!,
            priceInCents = 350 ,
            available = true,
            description = "Sedap"
        ),
        52 to Food(
            foodId = 52 ,
            foodName = "Ribena ",
            category = category[3]!!,
            priceInCents = 400 ,
            available = true,
            description = "Sedap"
        ),
        53 to Food(
            foodId = 53 ,
            foodName = " Ribena Sprite",
            category = category[3]!!,
            priceInCents = 450 ,
            available = true,
            description = "Sedap"
        ),
        54 to Food(
            foodId = 54 ,
            foodName = " Wheatgrass C",
            category = category[3]!!,
            priceInCents = 450 ,
            available = true,
            description = "Sedap"
        ),
        55 to Food(
            foodId = 55 ,
            foodName = " Jagung ",
            category = category[3]!!,
            priceInCents = 400 ,
            available = true,
            description = "Sedap"
        ),
        56 to Food(
            foodId = 56 ,
            foodName = " Bandung ",
            category = category[3]!!,
            priceInCents = 450 ,
            available = true,
            description = "Sedap"
        ),

        57 to Food(
            foodId = 57 ,
            foodName = " Bandung Susu",
            category = category[3]!!,
            priceInCents = 500 ,
            available = true,
            description = "Sedap"
        ),

        58 to Food(
            foodId = 58 ,
            foodName = " Bandung Laici",
            category = category[3]!!,
            priceInCents = 500 ,
            available = true,
            description = "Sedap"
        ),

        59 to Food(
            foodId = 59 ,
            foodName = " Bandung Cincau",
            category = category[3]!!,
            priceInCents = 550 ,
            available = true,
            description = "Sedap"
        ),

        60 to Food(
            foodId = 60 ,
            foodName = " Green Tea",
            category = category[3]!!,
            priceInCents = 300 ,
            available = true,
            description = "Sedap"
        ),
        61 to Food(
            foodId = 61 ,
            foodName = " Green Tea Lemon",
            category = category[3]!!,
            priceInCents = 350 ,
            available = true,
            description = "Sedap"
        ),
        62 to Food(
            foodId = 62 ,
            foodName = " Longan ",
            category = category[3]!!,
            priceInCents = 400 ,
            available = true,
            description = "Sedap"
        ),
        63 to Food(
            foodId = 63 ,
            foodName = " Longan Susu",
            category = category[3]!!,
            priceInCents = 450 ,
            available = true,
            description = "Sedap"
        ),
        64 to Food(
            foodId = 64 ,
            foodName = " Longan Lemon",
            category = category[3]!!,
            priceInCents = 450 ,
            available = true,
            description = "Sedap"
        ),
        65 to Food(
            foodId = 65 ,
            foodName = " Laici ",
            category = category[3]!!,
            priceInCents = 350 ,
            available = true,
            description = "Sedap"
        ),
        66 to Food(
            foodId = 66 ,
            foodName = " Laici Susu",
            category = category[3]!!,
            priceInCents = 400 ,
            available = true,
            description = "Sedap"
        ),
        67 to Food(
            foodId = 67 ,
            foodName = " Laici Lemon",
            category = category[3]!!,
            priceInCents = 400 ,
            available = true,
            description = "Sedap"
        ),
        68 to Food(
            foodId = 68 ,
            foodName = " Lemon ",
            category = category[3]!!,
            priceInCents = 300 ,
            available = true,
            description = "Sedap"
        ),
        69 to Food(
            foodId = 69 ,
            foodName = " Limau ",
            category = category[3]!!,
            priceInCents = 300 ,
            available = true,
            description = "Sedap"
        ),
        70 to Food(
            foodId = 70 ,
            foodName = " Asamboi Oren",
            category = category[3]!!,
            priceInCents = 400 ,
            available = true,
            description = "Sedap"
        ),
        71 to Food(
            foodId = 71 ,
            foodName = " Asamboi Putih",
            category = category[3]!!,
            priceInCents = 400 ,
            available = true,
            description = "Sedap"
        ),
        72 to Food(
            foodId = 72 ,
            foodName = " Lemon Samboi",
            category = category[3]!!,
            priceInCents = 400 ,
            available = true,
            description = "Sedap"
        ),
        73 to Food(
            foodId = 73 ,
            foodName = " Limau Samboi",
            category = category[3]!!,
            priceInCents = 350 ,
            available = true,
            description = "Sedap"
        ),
        74 to Food(
            foodId = 74 ,
            foodName = " Barli ",
            category = category[3]!!,
            priceInCents = 300 ,
            available = true,
            description = "Sedap"
        ),
        75 to Food(
            foodId = 75 ,
            foodName = " Barli Lemon",
            category = category[3]!!,
            priceInCents = 350 ,
            available = true,
            description = "Sedap"
        ),
        76 to Food(
            foodId = 76 ,
            foodName = " Barli Susu",
            category = category[3]!!,
            priceInCents = 400 ,
            available = true,
            description = "Sedap"
        ),
        77 to Food(
            foodId = 77 ,
            foodName = "Kundur ",
            category = category[3]!!,
            priceInCents = 300 ,
            available = true,
            description = "Sedap"
        ),
        78 to Food(
            foodId = 78 ,
            foodName = "Blackcurrant ",
            category = category[3]!!,
            priceInCents = 400 ,
            available = true,
            description = "Sedap"
        ),
        79 to Food(
            foodId = 79 ,
            foodName = " BlueLagoon ",
            category = category[3]!!,
            priceInCents = 400 ,
            available = true,
            description = "Sedap"
        ),
        80 to Food(
            foodId = 80 ,
            foodName = " Bluerose ",
            category = category[3]!!,
            priceInCents = 400 ,
            available = true,
            description = "Sedap"
        ),

        81 to Food(
            foodId = 81 ,
            foodName = " Jambu batu",
            category = category[3]!!,
            priceInCents = 500 ,
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