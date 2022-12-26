package com.example.aairastation.feature_order.domain.use_case.table

import com.example.aairastation.domain.MainRepository

class GetTable(
    private val repository: MainRepository
) {
    operator fun invoke(id: Long) = repository.getTable(id)
}