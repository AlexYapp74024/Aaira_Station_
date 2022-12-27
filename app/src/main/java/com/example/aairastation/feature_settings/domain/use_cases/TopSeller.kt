package com.example.aairastation.feature_settings.domain.use_cases

import com.example.aairastation.domain.MainRepository

class TopSeller(
    private val repository: MainRepository
) {

}

sealed class TimeGrouping(
    val fromTime: Long,
    val toTime: Long,
) {
    class Daily(fromTime: Long, toTime: Long) : TimeGrouping(fromTime, toTime)
    class Weekly(fromTime: Long, toTime: Long) : TimeGrouping(fromTime, toTime)
    class Monthly(fromTime: Long, toTime: Long) : TimeGrouping(fromTime, toTime)
    class Yearly(fromTime: Long, toTime: Long) : TimeGrouping(fromTime, toTime)
}