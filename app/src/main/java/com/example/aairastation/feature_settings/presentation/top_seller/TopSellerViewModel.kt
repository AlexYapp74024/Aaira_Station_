package com.example.aairastation.feature_settings.presentation.top_seller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aairastation.core.firstDayOf
import com.example.aairastation.core.lastDayOf
import com.example.aairastation.feature_order.domain.use_case.OrderUseCases
import com.example.aairastation.feature_settings.domain.use_cases.ParseOrderDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import javax.inject.Inject

@HiltViewModel
class TopSellerViewModel @Inject constructor(
    useCases: OrderUseCases
) : ViewModel() {

    private var _grouping = MutableStateFlow<TimeGrouping>(TimeGrouping.Daily)
    val grouping = _grouping.asStateFlow()

    private var shiftInterval = MutableStateFlow(0)

    val fromDate = combine(shiftInterval, grouping) { shiftInterval, filter ->
        Clock.System.todayIn(TimeZone.currentSystemDefault())
            .minus(shiftInterval, filter.dateTimeUnit)
            .firstDayOf(filter.dateTimeUnit)
    }

    val toDate = combine(shiftInterval, grouping) { shiftInterval, filter ->
        Clock.System.todayIn(TimeZone.currentSystemDefault())
            .minus(shiftInterval, filter.dateTimeUnit)
            .lastDayOf(filter.dateTimeUnit)
    }

    private val details = useCases.getAllDetail()
    val filtered = combine(fromDate, toDate, details) { fromTime, toTime, details ->
        ParseOrderDetails()(details).filter {
            it.creationTime in fromTime..toTime
        }
    }

    fun setGrouping(grouping: TimeGrouping) = viewModelScope.launch {
        _grouping.value = grouping
    }

    fun shiftTimeForward() = viewModelScope.launch {
        if (shiftInterval.value > 0)
            shiftInterval.value = shiftInterval.value - 1
    }

    fun shiftTimeBackward() = viewModelScope.launch {
        shiftInterval.value = shiftInterval.value + 1
    }
}

enum class TimeGrouping(
    val dateTimeUnit: DateTimeUnit.DateBased,
) {
    Daily(DateTimeUnit.DAY),
    Weekly(DateTimeUnit.WEEK),
    Monthly(DateTimeUnit.MONTH),
    Yearly(DateTimeUnit.YEAR),
}