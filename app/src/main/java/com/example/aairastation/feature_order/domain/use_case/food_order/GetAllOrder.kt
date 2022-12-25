package com.example.aairastation.feature_order.domain.use_case.food_order

import com.example.aairastation.domain.MainRepository

class GetAllOrder(
    private val repository: MainRepository,
) {
    operator fun invoke() = repository.getAllOrder()
}