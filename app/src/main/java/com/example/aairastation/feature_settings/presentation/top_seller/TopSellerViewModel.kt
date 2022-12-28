package com.example.aairastation.feature_settings.presentation.top_seller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aairastation.core.firstDayOf
import com.example.aairastation.core.lastDayOf
import com.example.aairastation.feature_order.domain.use_case.OrderUseCases
import com.example.aairastation.feature_settings.domain.model.TimeGrouping
import com.example.aairastation.feature_settings.domain.use_cases.ParseOrderDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.todayIn
import javax.inject.Inject
import com.example.aairastation.feature_settings.domain.model.Grouping as Grouping

@HiltViewModel
class TopSellerViewModel @Inject constructor(
    useCases: OrderUseCases
) : ViewModel() {

    private val details = useCases.getAllDetail()

    private var _timeGrouping = MutableStateFlow(TimeGrouping.Daily)
    val timeGrouping = _timeGrouping.asStateFlow()

    private var shiftInterval = MutableStateFlow(0)

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

    val filtered = combine(fromDate, toDate, details) { fromTime, toTime, details ->
        ParseOrderDetails()(details).filter {
            it.creationTime in fromTime..toTime
        }
    }

    private var _grouping = MutableStateFlow(Grouping.Price)
    var grouping = _grouping.asStateFlow()

    fun setTimeGrouping(timeGrouping: TimeGrouping) {
        _timeGrouping.value = timeGrouping
    }

    fun setGrouping(grouping: Grouping) {
        _grouping.value = grouping
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
