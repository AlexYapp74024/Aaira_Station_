package com.example.aairastation.feature_settings.presentation.sales_report

import androidx.lifecycle.ViewModel
import com.example.aairastation.core.firstDayOf
import com.example.aairastation.core.lastDayOf
import com.example.aairastation.core.minus
import com.example.aairastation.feature_settings.domain.model.FoodStatsItem
import com.example.aairastation.feature_settings.domain.model.TimeGrouping
import com.example.aairastation.feature_settings.domain.use_cases.SettingsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import javax.inject.Inject

@HiltViewModel
class SalesReportViewModel @Inject constructor(
    useCases: SettingsUseCases
) : ViewModel() {

    /**
     * A custom class used to organize data that will be displayed
     */
    data class Entry(
        val number: Int,
        val dateRange: String,
        val total: Int
    )

    /**
     * Retrieve data from the database
     */
    private val details: Flow<List<FoodStatsItem>> = useCases.getAndParseSales()

    private var _timeGrouping: MutableStateFlow<TimeGrouping> = MutableStateFlow(TimeGrouping.Daily)
    val timeGrouping: StateFlow<TimeGrouping> = _timeGrouping.asStateFlow()

    val entries: Flow<List<Entry>> = combine(details, timeGrouping) { details, timeGrouping ->
        // Get today or the last day of week, month or year, depending on timeGrouping
        val startingDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
            .lastDayOf(timeGrouping.dateTimeUnit)

        details.groupBy {
            // Group each entry by distance from starting date
            startingDate.minus(it.creationTime, timeGrouping.dateTimeUnit)
        }.map { (number, items) ->
            // Convert each items to an Entry class for easy display
            val total = items.fold(0) { acc, item ->
                acc + item.price
            }
            val fromDate = items.first().creationTime.firstDayOf(timeGrouping.dateTimeUnit)
            val toDate = items.first().creationTime.lastDayOf(timeGrouping.dateTimeUnit)

            val dateRange = when (timeGrouping) {
                TimeGrouping.Daily -> formatTime(fromDate)
                else -> "${formatTime(fromDate)} - ${formatTime(toDate)}"
            }

            Entry(number, dateRange, total)
        }
    }

    fun setTimeGrouping(timeGrouping: TimeGrouping) {
        _timeGrouping.value = timeGrouping
    }

    private fun formatTime(date: LocalDate) = when (timeGrouping.value) {
        TimeGrouping.Daily,
        TimeGrouping.Weekly ->
            "${date.dayOfMonth}/${date.monthNumber}"
        TimeGrouping.Monthly ->
            "${date.monthNumber}/${date.year}"
        TimeGrouping.Yearly ->
            "${date.year}"
    }
}



