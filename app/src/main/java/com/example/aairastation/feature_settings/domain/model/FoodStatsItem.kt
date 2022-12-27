package com.example.aairastation.feature_settings.domain.model

import com.example.aairastation.feature_order.domain.model.OrderDetail

data class FoodStatsItem(
    val foodName: String,
    val price: Int,
    val amount: Int,
    val creationTime: Long
) {
    companion object {
        fun fromDetail(detail: OrderDetail) = FoodStatsItem(
            foodName = detail.food.foodName,
            price = detail.food.priceInCents,
            amount = detail.amount,
            creationTime = detail.order.createdAt,
        )
    }
}
