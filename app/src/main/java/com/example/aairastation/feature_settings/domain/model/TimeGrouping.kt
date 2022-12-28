package com.example.aairastation.feature_settings.domain.model

import kotlinx.datetime.DateTimeUnit

enum class TimeGrouping(
    val groupName: String,
    val dateTimeUnit: DateTimeUnit.DateBased,
) {
    Daily(
        "Daily", DateTimeUnit.DAY
    ),
    Weekly(
        "Weekly", DateTimeUnit.WEEK
    ),
    Monthly(
        "Monthly", DateTimeUnit.MONTH
    ),
    Yearly(
        "Yearly", DateTimeUnit.YEAR
    ),
}