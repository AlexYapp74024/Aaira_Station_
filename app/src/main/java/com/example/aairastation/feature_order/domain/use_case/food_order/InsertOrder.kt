package com.example.aairastation.feature_order.domain.use_case.food_order

import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_order.domain.model.FoodOrder

class InsertOrder(
    private val repository: MainRepository,
) {
    suspend operator fun invoke(order: FoodOrder): Long = repository.insertOrder(order)
}