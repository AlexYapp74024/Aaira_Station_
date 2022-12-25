package com.example.aairastation.feature_order.domain.use_case.order_detail

import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_order.domain.model.OrderDetail

class InsertDetail(
    private val repository: MainRepository,
) {
    suspend operator fun invoke(detail: OrderDetail): Long = repository.insertOrderDetail(detail)
}