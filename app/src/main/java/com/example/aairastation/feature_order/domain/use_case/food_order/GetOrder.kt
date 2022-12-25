package com.example.aairastation.feature_order.domain.use_case.food_order

import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_order.domain.model.FoodOrder
import kotlinx.coroutines.flow.Flow

class GetOrder(
    private val repository: MainRepository,
) {
    operator fun invoke(id: Long): Flow<FoodOrder?> = repository.getOrder(id)
}