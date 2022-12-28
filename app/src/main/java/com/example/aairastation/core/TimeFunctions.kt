package com.example.aairastation.core

import kotlinx.datetime.*

fun timeFromNow(
    daysAgo: Int = 0,
    weeksAgo: Int = 0,
    monthsAgo: Int = 0,
    yearsAgo: Int = 0,
): Instant {
    val now = Clock.System.now()
    val systemTZ = TimeZone.currentSystemDefault()
    return now
        .minus(daysAgo, DateTimeUnit.DAY, systemTZ)
        .minus(weeksAgo, DateTimeUnit.WEEK, systemTZ)
        .minus(monthsAgo, DateTimeUnit.MONTH, systemTZ)
        .minus(yearsAgo, DateTimeUnit.YEAR, systemTZ)
}

@Suppress("KotlinConstantConditions")
fun LocalDate.firstDayOf(dateTimeUnit: DateTimeUnit.DateBased) = when (dateTimeUnit) {
    DateTimeUnit.DAY -> this
    DateTimeUnit.WEEK -> this.firstDayOfWeek()
    DateTimeUnit.MONTH -> this.firstDayOfMonth()
    DateTimeUnit.YEAR -> this.firstDayOfYear()
    else -> throw UnsupportedOperationException("Date Time Unit not supported")
}

@Suppress("KotlinConstantConditions")
fun LocalDate.lastDayOf(dateTimeUnit: DateTimeUnit.DateBased) = when (dateTimeUnit) {
    DateTimeUnit.DAY -> this
    DateTimeUnit.WEEK -> this.lastDayOfWeek()
    DateTimeUnit.MONTH -> this.lastDayOfMonth()
    DateTimeUnit.YEAR -> this.lastDayOfYear()
    else -> throw UnsupportedOperationException("Date Time Unit not supported")
}

fun LocalDate.firstDayOfWeek() = this - DatePeriod(days = dayOfWeek.value % 7)
fun LocalDate.firstDayOfMonth() = this - DatePeriod(days = dayOfMonth - 1)
fun LocalDate.firstDayOfYear() = this - DatePeriod(days = dayOfYear - 1)

fun LocalDate.lastDayOfWeek() = this.firstDayOfWeek() + DatePeriod(days = 6)
fun LocalDate.lastDayOfMonth() =
    (this + DatePeriod(months = 1)).firstDayOfMonth() - DatePeriod(days = 1)

fun LocalDate.lastDayOfYear() =
    (this + DatePeriod(years = 1)).firstDayOfYear() - DatePeriod(days = 1)

fun LocalDate.format() = "$dayOfMonth/$monthNumber/${year % 100}"

fun LocalDate.Companion.formatRange(from: LocalDate, to: LocalDate) =
    if (from == to) from.format()
    else "${from.format()} - ${to.format()}"

@Suppress("KotlinConstantConditions")
fun LocalDate.minus(other: LocalDate, unit: DateTimeUnit.DateBased): Int {
    return when (unit) {
        DateTimeUnit.DAY -> {
            this.toEpochDays() - other.toEpochDays()
        }
        DateTimeUnit.WEEK -> {
            (this.toEpochDays() - other.toEpochDays()) / 7
        }
        DateTimeUnit.MONTH -> {
            val difference = this - other
            difference.months + difference.years * 12
        }
        DateTimeUnit.YEAR -> {
            (this- other).years * 12
        }
        else -> throw UnsupportedOperationException("Date Time Unit not supported")
    }
}


