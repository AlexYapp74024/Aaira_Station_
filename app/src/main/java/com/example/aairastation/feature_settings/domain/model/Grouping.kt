package com.example.aairastation.feature_settings.domain.model

enum class Grouping(
    val valueOf: (FoodStatsItem) -> Int
) {
    Price(
        { it.price * it.amount }
    ),
    Amount(
        { it.amount }
    ),
}