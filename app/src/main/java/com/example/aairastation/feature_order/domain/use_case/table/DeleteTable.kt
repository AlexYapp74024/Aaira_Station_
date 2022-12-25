package com.example.aairastation.feature_order.domain.use_case.table

import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_order.domain.model.NumberedTable

class DeleteTable(
    private val repository: MainRepository,
) {
    suspend operator fun invoke(table: NumberedTable) {
        repository.deleteTable(table)
    }
}