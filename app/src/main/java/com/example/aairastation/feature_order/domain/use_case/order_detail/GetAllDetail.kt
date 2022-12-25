package com.example.aairastation.feature_order.domain.use_case.order_detail

import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_order.domain.model.OrderDetail
import kotlinx.coroutines.flow.Flow

class GetAllDetail(
    private val repository: MainRepository,
) {
    operator fun invoke(): Flow<List<OrderDetail>> = repository.getAllOrderDetail()
}