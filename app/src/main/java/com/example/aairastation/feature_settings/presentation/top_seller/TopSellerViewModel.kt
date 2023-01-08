package com.example.aairastation.feature_settings.presentation.top_seller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aairastation.core.firstDayOf
import com.example.aairastation.core.formatRange
import com.example.aairastation.core.lastDayOf
import com.example.aairastation.feature_settings.domain.model.Grouping
import com.example.aairastation.feature_settings.domain.model.TimeGrouping
import com.example.aairastation.feature_settings.domain.use_cases.SettingsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import javax.inject.Inject

@HiltViewModel
class TopSellerViewModel @Inject constructor(
    useCases: SettingsUseCases
) : ViewModel() {

    /**
     * Order Details retrieved from the database
     */
    private val details = useCases.getAndParseSales()

    private var _timeGrouping = MutableStateFlow(TimeGrouping.Daily)
    val timeGrouping = _timeGrouping.asStateFlow()

    private var shiftInterval = MutableStateFlow(0)

    /**
     * [fromDate] and [toDate] will be calculated based on the timeGrouping chosen and the shift interval
     */
    private val fromDate = combine(shiftInterval, timeGrouping) { shiftInterval, filter ->
        Clock.System.todayIn(TimeZone.currentSystemDefault())
            .minus(shiftInterval, filter.dateTimeUnit)
            .firstDayOf(filter.dateTimeUnit)
    }

    private val toDate = combine(shiftInterval, timeGrouping) { shiftInterval, filter ->
        Clock.System.todayIn(TimeZone.currentSystemDefault())
            .minus(shiftInterval, filter.dateTimeUnit)
            .lastDayOf(filter.dateTimeUnit)
    }

    /**
     * Filter order details to only those with in the time frame
     */
    val filtered = combine(fromDate, toDate, details) { fromTime, toTime, details ->
        details.filter {
            it.creationTime in fromTime..toTime
        }
    }

    private var _grouping = MutableStateFlow(Grouping.Price)
    var grouping = _grouping.asStateFlow()

    fun setTimeGrouping(timeGrouping: TimeGrouping) {
        _timeGrouping.value = timeGrouping
        shiftInterval.value = 0
    }

    fun setGrouping(grouping: Grouping) {
        _grouping.value = grouping
    }

    /**
     * Format date range that will be displayed on the screen
     */
    val formattedDateRange = combine(fromDate, toDate) { fromDate, toDate ->
        LocalDate.formatRange(fromDate, toDate)
    }

    val items = combine(filtered, grouping) { filtered, grouping ->
        filtered.groupBy { it.foodName }
            .map { (foodName, data) ->
                val total = data.map { grouping.valueOf(it) }
                    .fold(0) { acc, value -> acc + value }
                foodName to total
            }.sortedByDescending { (_, total) ->
                total
            }
    }


    fun shiftTimeForward() = viewModelScope.launch {
        if (shiftInterval.value > 0)
            shiftInterval.value = shiftInterval.value - 1
    }

    fun shiftTimeBackward() = viewModelScope.launch {
        shiftInterval.value = shiftInterval.value + 1
    }
}
