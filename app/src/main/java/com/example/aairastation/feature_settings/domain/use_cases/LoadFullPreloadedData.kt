package com.example.aairastation.feature_settings.domain.use_cases

import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.example.aairastation.feature_order.domain.model.NumberedTable
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
            foodId = 10,
            foodName = " Milo Tarik",
            category = category[3]!!,
            priceInCents = 450,
            available = true,
            description = "Sedap"
        ),
        11 to Food(
            foodId = 11,
            foodName = "Milo Tabur",
            category = category[3]!!,
            priceInCents = 400,
            available = true,
            description = "Sedap"
        ),
        12 to Food(
            foodId = 12,
            foodName = " Milo Dinasour",
            category = category[3]!!,
            priceInCents = 500,
            available = true,
            description = "Sedap"
        ),
        13 to Food(
            foodId = 13,
            foodName = " Nescafé O",
            category = category[3]!!,
            priceInCents = 350,
            available = true,
            description = "Sedap"
        ),
        14 to Food(
            foodId = 14,
            foodName = " Nescafé Tarik",
            category = category[3]!!,
            priceInCents = 350,
            available = true,
            description = "Sedap"
        ),
        15 to Food(
            foodId = 15,
            foodName = " Nescafé Susu",
            category = category[3]!!,
            priceInCents = 350,
            available = true,
            description = "Sedap"
        ),
        16 to Food(
            foodId = 16,
            foodName = " Neslo ",
            category = category[3]!!,
            priceInCents = 400,
            available = true,
            description = "Sedap"
        ),
        17 to Food(
            foodId = 17,
            foodName = " Neslo Tarik",
            category = category[3]!!,
            priceInCents = 450,
            available = true,
            description = "Sedap"
        ),
        18 to Food(
            foodId = 18,
            foodName = " Horlickh ",
            category = category[3]!!,
            priceInCents = 600,
            available = true,
            description = "Sedap"
        ),
        19 to Food(
            foodId = 19,
            foodName = " Horlickh O",
            category = category[3]!!,
            priceInCents = 650,
            available = true,
            description = "Sedap"
        ),
        20 to Food(
            foodId = 20,
            foodName = " Horlickh Tarik",
            category = category[3]!!,
            priceInCents = 700,
            available = true,
            description = "Sedap"
        ),
        21 to Food(
            foodId = 21,
            foodName = " Jus Apple",
            category = category[3]!!,
            priceInCents = 600,
            available = true,
            description = "Sedap"
        ),
        22 to Food(
            foodId = 22,
            foodName = " Jus Oren",
            category = category[3]!!,
            priceInCents = 550,
            available = true,
            description = "Sedap"
        ),
        23 to Food(
            foodId = 23,
            foodName = " Jus Carrot",
            category = category[3]!!,
            priceInCents = 600,
            available = true,
            description = "Sedap"
        ),
        24 to Food(
            foodId = 24,
            foodName = " Jus Manggo",
            category = category[3]!!,
            priceInCents = 650,
            available = true,
            description = "Sedap"
        ),
        26 to Food(
            foodId = 26,
            foodName = " Jus Watermelon",
            category = category[3]!!,
            priceInCents = 600,
            available = true,
            description = "Sedap"
        ),
        27 to Food(
            foodId = 27,
            foodName = " Jus Dragonfruit",
            category = category[3]!!,
            priceInCents = 500,
            available = true,
            description = "Sedap"
        ),
        28 to Food(
            foodId = 28,
            foodName = " Chocolate ",
            category = category[3]!!,
            priceInCents = 400,
            available = true,
            description = "Sedap"
        ),
        29 to Food(
            foodId = 29,
            foodName = " Moccacino ",
            category = category[3]!!,
            priceInCents = 450,
            available = true,
            description = "Sedap"
        ),
        30 to Food(
            foodId = 30,
            foodName = " Bubble gum",
            category = category[3]!!,
            priceInCents = 450,
            available = true,
            description = "Sedap"
        ),
        31 to Food(
            foodId = 31,
            foodName = " Vanilla blue",
            category = category[3]!!,
            priceInCents = 450,
            available = true,
            description = "Sedap"
        ),
        32 to Food(
            foodId = 32,
            foodName = " Grape ",
            category = category[3]!!,
            priceInCents = 550,
            available = true,
            description = "Sedap"
        ),
        33 to Food(
            foodId = 33,
            foodName = " Guava ",
            category = category[3]!!,
            priceInCents = 500,
            available = true,
            description = "Sedap"
        ),
        34 to Food(
            foodId = 34,
            foodName = " Durian ",
            category = category[3]!!,
            priceInCents = 500,
            available = true,
            description = "Sedap"
        ),
        35 to Food(
            foodId = 35,
            foodName = " Manggo ",
            category = category[3]!!,
            priceInCents = 500,
            available = true,
            description = "Sedap"
        ),
        36 to Food(
            foodId = 36,
            foodName = " Honeydew ",
            category = category[3]!!,
            priceInCents = 500,
            available = true,
            description = "Sedap"
        ),
        37 to Food(
            foodId = 37,
            foodName = " Avocado ",
            category = category[3]!!,
            priceInCents = 500,
            available = true,
            description = "Sedap"
        ),
        38 to Food(
            foodId = 38,
            foodName = " Yogurt strawberry",
            category = category[3]!!,
            priceInCents = 600,
            available = true,
            description = "Sedap"
        ),
        39 to Food(
            foodId = 39,
            foodName = " Cola ",
            category = category[3]!!,
            priceInCents = 200,
            available = true,
            description = "Sedap"
        ),
        40 to Food(
            foodId = 40,
            foodName = " Pepsi ",
            category = category[3]!!,
            priceInCents = 200,
            available = true,
            description = "Sedap"
        ),
        41 to Food(
            foodId = 41,
            foodName = " Sprite ",
            category = category[3]!!,
            priceInCents = 200,
            available = true,
            description = "Sedap"
        ),
        42 to Food(
            foodId = 42,
            foodName = " 100plus ",
            category = category[3]!!,
            priceInCents = 250,
            available = true,
            description = "Sedap"
        ),
        43 to Food(
            foodId = 43,
            foodName = " Soda F&N",
            category = category[3]!!,
            priceInCents = 200,
            available = true,
            description = "Sedap"
        ),
        44 to Food(
            foodId = 44,
            foodName = " Sarsi ",
            category = category[3]!!,
            priceInCents = 200,
            available = true,
            description = "Sedap"
        ),
        45 to Food(
            foodId = 45,
            foodName = " Soya ",
            category = category[3]!!,
            priceInCents = 200,
            available = true,
            description = "Sedap"
        ),
        46 to Food(
            foodId = 46,
            foodName = " Cincau ",
            category = category[3]!!,
            priceInCents = 350,
            available = true,
            description = "Sedap"
        ),
        47 to Food(
            foodId = 47,
            foodName = " Teh Bunga",
            category = category[3]!!,
            priceInCents = 300,
            available = true,
            description = "Sedap"
        ),
        48 to Food(
            foodId = 48,
            foodName = " Sirap Rose",
            category = category[3]!!,
            priceInCents = 400,
            available = true,
            description = "Sedap"
        ),
        49 to Food(
            foodId = 49,
            foodName = " Sirap Oren",
            category = category[3]!!,
            priceInCents = 400,
            available = true,
            description = "Sedap"
        ),
        50 to Food(
            foodId = 50,
            foodName = " Sirap Sarsi",
            category = category[3]!!,
            priceInCents = 350,
            available = true,
            description = "Sedap"
        ),
        51 to Food(
            foodId = 51,
            foodName = " Sunquick oren",
            category = category[3]!!,
            priceInCents = 350,
            available = true,
            description = "Sedap"
        ),
        52 to Food(
            foodId = 52,
            foodName = "Ribena ",
            category = category[3]!!,
            priceInCents = 400,
            available = true,
            description = "Sedap"
        ),
        53 to Food(
            foodId = 53,
            foodName = " Ribena Sprite",
            category = category[3]!!,
            priceInCents = 450,
            available = true,
            description = "Sedap"
        ),
        54 to Food(
            foodId = 54,
            foodName = " Wheatgrass C",
            category = category[3]!!,
            priceInCents = 450,
            available = true,
            description = "Sedap"
        ),
        55 to Food(
            foodId = 55,
            foodName = " Jagung ",
            category = category[3]!!,
            priceInCents = 400,
            available = true,
            description = "Sedap"
        ),
        56 to Food(
            foodId = 56,
            foodName = " Bandung ",
            category = category[3]!!,
            priceInCents = 450,
            available = true,
            description = "Sedap"
        ),

        57 to Food(
            foodId = 57,
            foodName = " Bandung Susu",
            category = category[3]!!,
            priceInCents = 500,
            available = true,
            description = "Sedap"
        ),

        58 to Food(
            foodId = 58,
            foodName = " Bandung Laici",
            category = category[3]!!,
            priceInCents = 500,
            available = true,
            description = "Sedap"
        ),

        59 to Food(
            foodId = 59,
            foodName = " Bandung Cincau",
            category = category[3]!!,
            priceInCents = 550,
            available = true,
            description = "Sedap"
        ),

        60 to Food(
            foodId = 60,
            foodName = " Green Tea",
            category = category[3]!!,
            priceInCents = 300,
            available = true,
            description = "Sedap"
        ),
        61 to Food(
            foodId = 61,
            foodName = " Green Tea Lemon",
            category = category[3]!!,
            priceInCents = 350,
            available = true,
            description = "Sedap"
        ),
        62 to Food(
            foodId = 62,
            foodName = " Longan ",
            category = category[3]!!,
            priceInCents = 400,
            available = true,
            description = "Sedap"
        ),
        63 to Food(
            foodId = 63,
            foodName = " Longan Susu",
            category = category[3]!!,
            priceInCents = 450,
            available = true,
            description = "Sedap"
        ),
        64 to Food(
            foodId = 64,
            foodName = " Longan Lemon",
            category = category[3]!!,
            priceInCents = 450,
            available = true,
            description = "Sedap"
        ),
        65 to Food(
            foodId = 65,
            foodName = " Laici ",
            category = category[3]!!,
            priceInCents = 350,
            available = true,
            description = "Sedap"
        ),
        66 to Food(
            foodId = 66,
            foodName = " Laici Susu",
            category = category[3]!!,
            priceInCents = 400,
            available = true,
            description = "Sedap"
        ),
        67 to Food(
            foodId = 67,
            foodName = " Laici Lemon",
            category = category[3]!!,
            priceInCents = 400,
            available = true,
            description = "Sedap"
        ),
        68 to Food(
            foodId = 68,
            foodName = " Lemon ",
            category = category[3]!!,
            priceInCents = 300,
            available = true,
            description = "Sedap"
        ),
        69 to Food(
            foodId = 69,
            foodName = " Limau ",
            category = category[3]!!,
            priceInCents = 300,
            available = true,
            description = "Sedap"
        ),
        70 to Food(
            foodId = 70,
            foodName = " Asamboi Oren",
            category = category[3]!!,
            priceInCents = 400,
            available = true,
            description = "Sedap"
        ),
        71 to Food(
            foodId = 71,
            foodName = " Asamboi Putih",
            category = category[3]!!,
            priceInCents = 400,
            available = true,
            description = "Sedap"
        ),
        72 to Food(
            foodId = 72,
            foodName = " Lemon Samboi",
            category = category[3]!!,
            priceInCents = 400,
            available = true,
            description = "Sedap"
        ),
        73 to Food(
            foodId = 73,
            foodName = " Limau Samboi",
            category = category[3]!!,
            priceInCents = 350,
            available = true,
            description = "Sedap"
        ),
        74 to Food(
            foodId = 74,
            foodName = " Barli ",
            category = category[3]!!,
            priceInCents = 300,
            available = true,
            description = "Sedap"
        ),
        75 to Food(
            foodId = 75,
            foodName = " Barli Lemon",
            category = category[3]!!,
            priceInCents = 350,
            available = true,
            description = "Sedap"
        ),
        76 to Food(
            foodId = 76,
            foodName = " Barli Susu",
            category = category[3]!!,
            priceInCents = 400,
            available = true,
            description = "Sedap"
        ),
        77 to Food(
            foodId = 77,
            foodName = "Kundur ",
            category = category[3]!!,
            priceInCents = 300,
            available = true,
            description = "Sedap"
        ),
        78 to Food(
            foodId = 78,
            foodName = "Blackcurrant ",
            category = category[3]!!,
            priceInCents = 400,
            available = true,
            description = "Sedap"
        ),
        79 to Food(
            foodId = 79,
            foodName = " BlueLagoon ",
            category = category[3]!!,
            priceInCents = 400,
            available = true,
            description = "Sedap"
        ),
        80 to Food(
            foodId = 80,
            foodName = " Bluerose ",
            category = category[3]!!,
            priceInCents = 400,
            available = true,
            description = "Sedap"
        ),

        81 to Food(
            foodId = 81,
            foodName = " Jambu batu",
            category = category[3]!!,
            priceInCents = 500,
            available = true,
            description = "Sedap"
        ),

        82 to Food(
            foodId = 82,
            foodName = " Nasi Putih Ayam Berempah",
            category = category[1]!!,
            priceInCents = 700,
            available = true,
            description = "Sedap"
        ),

        83 to Food(
            foodId = 83,
            foodName = " Nasi Putih Ayam Mask Halia",
            category = category[1]!!,
            priceInCents = 800,
            available = true,
            description = "Sedap"
        ),

        84 to Food(
            foodId = 84,
            foodName = " Nasi Putih Daging Masak Halia",
            category = category[1]!!,
            priceInCents = 900,
            available = true,
            description = "Sedap"
        ),

        85 to Food(
            foodId = 85,
            foodName = " Nasi Putih Ayam Berempah",
            category = category[1]!!,
            priceInCents = 1000,
            available = true,
            description = "Sedap"
        ),

        86 to Food(
            foodId = 86,
            foodName = " Nasi Putih Ayom Kunyit",
            category = category[1]!!,
            priceInCents = 800,
            available = true,
            description = "Sedap"
        ),

        87 to Food(
            foodId = 87,
            foodName = " Nasi Putih Ayam Poprik",
            category = category[1]!!,
            priceInCents = 800,
            available = true,
            description = "Sedap"
        ),

        88 to Food(
            foodId = 88,
            foodName = " Nosi Putih Daging Paprik",
            category = category[1]!!,
            priceInCents = 900,
            available = true,
            description = "Sedap"
        ),

        89 to Food(
            foodId = 89,
            foodName = " Nosi Putih Paprik Campur",
            category = category[1]!!,
            priceInCents = 900,
            available = true,
            description = "Sedap"
        ),

        90 to Food(
            foodId = 90,
            foodName = " Nasi Putih Ayam Butter",
            category = category[1]!!,
            priceInCents = 900,
            available = true,
            description = "Sedap"
        ),

        91 to Food(
            foodId = 91,
            foodName = " Lelapan lkan Keli",
            category = category[1]!!,
            priceInCents = 1500,
            available = true,
            description = "Sedap"
        ),

        92 to Food(
            foodId = 92,
            foodName = " Lelapan Ikan Talopia",
            category = category[1]!!,
            priceInCents = 1500,
            available = true,
            description = "Sedap"
        ),

        93 to Food(
            foodId = 93,
            foodName = " Nosi Kerabu Ayam Berempah",
            category = category[1]!!,
            priceInCents = 1300,
            available = true,
            description = "Sedap"
        ),

        94 to Food(
            foodId = 94,
            foodName = " Nasi Kerabu Ikan Kembung",
            category = category[1]!!,
            priceInCents = 1300,
            available = true,
            description = "Sedap"
        ),

        95 to Food(
            foodId = 95,
            foodName = " Nasi Kerabu lkan Keli",
            category = category[1]!!,
            priceInCents = 1300,
            available = true,
            description = "Sedap"
        ),

        96 to Food(
            foodId = 96,
            foodName = " Nasi Goreng Ayam Crispy",
            category = category[1]!!,
            priceInCents = 500,
            available = true,
            description = "Sedap"
        ),

        97 to Food(
            foodId = 97,
            foodName = " Nasi Goreng Ikan Masin",
            category = category[1]!!,
            priceInCents = 750,
            available = true,
            description = "Sedap"
        ),

        98 to Food(
            foodId = 98,
            foodName = " Nasi Goreng Daging",
            category = category[1]!!,
            priceInCents = 800,
            available = true,
            description = "Sedap"
        ),

        99 to Food(
            foodId = 99,
            foodName = " Nasi Goreng Seafood",
            category = category[1]!!,
            priceInCents = 800,
            available = true,
            description = "Sedap"
        ),

        100 to Food(
            foodId = 100,
            foodName = " Nasi Coreng Pataya",
            category = category[1]!!,
            priceInCents = 900,
            available = true,
            description = "Sedap"
        ),

        101 to Food(
            foodId = 101,
            foodName = " Nasi Goreng Cina",
            category = category[1]!!,
            priceInCents = 900,
            available = true,
            description = "Sedap"
        ),

        102 to Food(
            foodId = 102,
            foodName = " Nasi Goreng Dabai",
            category = category[1]!!,
            priceInCents = 900,
            available = true,
            description = "Sedap"
        ),

        103 to Food(
            foodId = 103,
            foodName = " Nasi Goreng Udang",
            category = category[1]!!,
            priceInCents = 800,
            available = true,
            description = "Sedap"
        ),

        104 to Food(
            foodId = 104,
            foodName = " Nasi Goreng Sotong",
            category = category[1]!!,
            priceInCents = 700,
            available = true,
            description = "Sedap"
        ),

        105 to Food(
            foodId = 105,
            foodName = " Nasi Goreng Kerang",
            category = category[1]!!,
            priceInCents = 900,
            available = true,
            description = "Sedap"
        ),

        106 to Food(
            foodId = 106,
            foodName = " Nasi Putih",
            category = category[4]!!,
            priceInCents = 200,
            available = true,
            description = "Sedap"
        ),


        107 to Food(
            foodId = 107,
            foodName = " Telur Mata",
            category = category[4]!!,
            priceInCents = 200,
            available = true,
            description = "Sedap"
        ),


        108 to Food(
            foodId = 108,
            foodName = " Telur Dadar",
            category = category[4]!!,
            priceInCents = 300,
            available = true,
            description = "Sedap"
        ),

        109 to Food(
            foodId = 109,
            foodName = " Mee Goreng Ayam",
            category = category[2]!!,
            priceInCents = 700,
            available = true,
            description = "Sedap"
        ),

        110 to Food(
            foodId = 110,
            foodName = " Mee Goreng Daging",
            category = category[2]!!,
            priceInCents = 800,
            available = true,
            description = "Sedap"
        ),

        111 to Food(
            foodId = 111,
            foodName = " Moo Gorong Seafood",
            category = category[2]!!,
            priceInCents = 900,
            available = true,
            description = "Sedap"
        ),

        112 to Food(
            foodId = 112,
            foodName = " Mee Sup Tulang",
            category = category[2]!!,
            priceInCents = 800,
            available = true,
            description = "Sedap"
        ),

        113 to Food(
            foodId = 113,
            foodName = " Meg Hoon Goreng Ayam",
            category = category[2]!!,
            priceInCents = 700,
            available = true,
            description = "Sedap"
        ),

        114 to Food(
            foodId = 114,
            foodName = " Mee Hoon Goreng Daging",
            category = category[2]!!,
            priceInCents = 800,
            available = true,
            description = "Sedap"
        ),

        115 to Food(
            foodId = 115,
            foodName = " Mee Hoon Goreng Seafood",
            category = category[2]!!,
            priceInCents = 900,
            available = true,
            description = "Sedap"
        ),

        116 to Food(
            foodId = 116,
            foodName = " Mee Hoon Sup",
            category = category[2]!!,
            priceInCents = 800,
            available = true,
            description = "Sedap"
        ),

        117 to Food(
            foodId = 117,
            foodName = " Kuey Teow Goreng Ayam",
            category = category[2]!!,
            priceInCents = 700,
            available = true,
            description = "Sedap"
        ),

        118 to Food(
            foodId = 118,
            foodName = " Kuey Teow Goreng Daging",
            category = category[2]!!,
            priceInCents = 800,
            available = true,
            description = "Sedap"
        ),

        119 to Food(
            foodId = 119,
            foodName = " Kuey Teow Goreng Seafood",
            category = category[2]!!,
            priceInCents = 900,
            available = true,
            description = "Sedap"
        ),

        120 to Food(
            foodId = 120,
            foodName = " Kuey Teow Sup",
            category = category[2]!!,
            priceInCents = 800,
            available = true,
            description = "Sedap"
        ),


        121 to Food(
            foodId = 121,
            foodName = " Mee Udang Galah",
            category = category[2]!!,
            priceInCents = 900,
            available = true,
            description = "Sedap"
        ),


        122 to Food(
            foodId = 122,
            foodName = " Char Kuey Teow Ayam",
            category = category[2]!!,
            priceInCents = 700,
            available = true,
            description = "Sedap"
        ),


        123 to Food(
            foodId = 123,
            foodName = " Char Kuey Teow Daging",
            category = category[2]!!,
            priceInCents = 800,
            available = true,
            description = "Sedap"
        ),


        124 to Food(
            foodId = 124,
            foodName = " Char Kuey Teow Sotong",
            category = category[2]!!,
            priceInCents = 800,
            available = true,
            description = "Sedap"
        ),


        125 to Food(
            foodId = 125,
            foodName = " Char Kuey Teow Udang",
            category = category[2]!!,
            priceInCents = 800,
            available = true,
            description = "Sedap"
        ),

        126 to Food(
            foodId = 125,
            foodName = " Char Kuey Teow Kerang",
            category = category[2]!!,
            priceInCents = 800,
            available = true,
            description = "Sedap"
        ),

        127 to Food(
            foodId = 125,
            foodName = " Char Kuey Teow Seafood ",
            category = category[2]!!,
            priceInCents = 950,
            available = true,
            description = "Sedap"
        ),

        128 to Food(
            foodId = 125,
            foodName = " Char Kuey Teow Special ",
            category = category[2]!!,
            priceInCents = 1050,
            available = true,
            description = "Sedap"
        ),

        129 to Food(
            foodId = 129,
            foodName = " Tom Yam Seafood ",
            category = category[4]!!,
            priceInCents = 1500,
            available = true,
            description = "Sedap"
        ),

        130 to Food(
            foodId = 129,
            foodName = " Tom Yam Ayam ",
            category = category[4]!!,
            priceInCents = 1300,
            available = true,
            description = "Sedap"
        ),

        131 to Food(
            foodId = 131,
            foodName = " Sup Tulang ",
            category = category[4]!!,
            priceInCents = 1300,
            available = true,
            description = "Sedap"
        ),


        )
    private val table = mapOf(
        1 to NumberedTable(1, 1),
        2 to NumberedTable(2, 2),
        3 to NumberedTable(3, 3),
    )
}