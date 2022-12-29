package com.example.aairastation.feature_settings.domain.use_cases

import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_settings.domain.model.FoodStatsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAndParseSales(
    private val repository: MainRepository
) {
    operator fun invoke(): Flow<List<FoodStatsItem>> =
        repository.getAllOrderDetail().map { details ->
            details.groupBy {
                it.order
            }.filter { (_, details) ->
                details.fold(true) { acc, detail ->
                    acc && detail.completed
                }
            }.map { (_, details) ->
                details.map {
                    FoodStatsItem.fromDetail(it)
                }
            }.flatten()
        }
}