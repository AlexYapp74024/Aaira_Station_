package com.example.aairastation.feature_settings.domain.use_cases

import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_settings.domain.model.FoodStatsItem
import kotlinx.coroutines.flow.map

class GetAndParseSales(
    private val repository: MainRepository
) {
    operator fun invoke() = repository.getAllOrderDetail().map { details ->
        details.filter {
            it.completed
        }.map {
            FoodStatsItem.fromDetail(it)
        }
    }
}