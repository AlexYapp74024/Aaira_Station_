package com.example.aairastation.feature_order.domain.use_case.table

import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_order.domain.model.NumberedTable
import kotlinx.coroutines.flow.Flow

class GetAllTable(
    private val repository: MainRepository,
) {
    operator fun invoke(): Flow<List<NumberedTable>> = repository.getAllTable()
}