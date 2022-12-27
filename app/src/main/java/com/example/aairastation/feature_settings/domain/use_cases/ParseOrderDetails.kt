package com.example.aairastation.feature_settings.domain.use_cases

import com.example.aairastation.feature_order.domain.model.OrderDetail
import com.example.aairastation.feature_settings.domain.model.FoodStatsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ParseOrderDetails {
    operator fun invoke(detail: OrderDetail) = FoodStatsItem.fromDetail(detail)
    operator fun invoke(details: List<OrderDetail>) = details.map { this(it) }
    operator fun invoke(details: Flow<List<OrderDetail>>) = details.map { this(it) }
}