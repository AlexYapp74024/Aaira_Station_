package com.example.aairastation.feature_settings.domain.use_cases

import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_settings.domain.model.FoodStatsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Takes all completed orders from the database and only extracts important information
 */
class GetAndParseSales(
    private val repository: MainRepository
) {
    operator fun invoke(): Flow<List<FoodStatsItem>> =
        repository.getAllOrderDetail().map { details ->
            details.groupBy {
                /**
                 * Group details by their parent order
                 */
                it.order
            }.filter { (_, details) ->
                /**
                 * Only take orders when all details are completed
                 */
                details.fold(true) { acc, detail ->
                    acc && detail.completed
                }
            }.map { (_, details) ->
                /**
                 * Convert each details to a [FoodStatsItem] object
                 */
                details.map {
                    FoodStatsItem.fromDetail(it)
                }
            }.flatten()
            /** Flatten the List of Lists into just a List */
        }
}