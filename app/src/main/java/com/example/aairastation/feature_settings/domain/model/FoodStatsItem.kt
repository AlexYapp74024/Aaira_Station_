package com.example.aairastation.feature_settings.domain.model

import com.example.aairastation.feature_order.domain.model.OrderDetail
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class FoodStatsItem(
    val foodName: String,
    val price: Int,
    val amount: Int,
    val creationTime: LocalDate
) {
    companion object {
        fun fromDetail(detail: OrderDetail) = FoodStatsItem(
            foodName = detail.food.foodName,
            price = detail.food.priceInCents,
            amount = detail.amount,
            creationTime = Instant
                .fromEpochMilliseconds(detail.order.createdAt)
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .date,
        )
    }
}
