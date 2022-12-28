package com.example.aairastation.feature_settings.presentation.sales_report

import androidx.lifecycle.ViewModel
import com.example.aairastation.core.firstDayOf
import com.example.aairastation.core.lastDayOf
import com.example.aairastation.core.minus
import com.example.aairastation.feature_order.domain.use_case.OrderUseCases
import com.example.aairastation.feature_settings.domain.model.TimeGrouping
import com.example.aairastation.feature_settings.domain.use_cases.ParseOrderDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import javax.inject.Inject

@HiltViewModel
class SalesReportViewModel @Inject constructor(
    useCases: OrderUseCases
) : ViewModel() {

    data class Entry(
        val number: Int,
        val dateRange: String,
        val total: Int
    )

    private val details = ParseOrderDetails()(useCases.getAllDetail())

    private var _timeGrouping = MutableStateFlow(TimeGrouping.Daily)
    val timeGrouping = _timeGrouping.asStateFlow()

    val entries = combine(details, timeGrouping) { details, timeGrouping ->
        val startingDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
            .lastDayOf(timeGrouping.dateTimeUnit)

        details.groupBy {
            startingDate.minus(it.creationTime, timeGrouping.dateTimeUnit)
        }.map { (number, items) ->
            val total = items.fold(0) { acc, item ->
                acc + item.price
            }
            val fromDate = items.first().creationTime.firstDayOf(timeGrouping.dateTimeUnit)
            val toDate = items.first().creationTime.lastDayOf(timeGrouping.dateTimeUnit)

            val dateRange = "${formatTime(fromDate)}-${formatTime(toDate)}"
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



