package com.example.aairastation.core

import com.google.common.truth.Truth.assertThat
import kotlinx.datetime.*
import org.junit.Test

class TimeFunctionsKtTest {

    private val testDate = LocalDate(2022, 11, 24)

    @Test
    fun `Correct first Day of week`() {
        assertThat(testDate.firstDayOfWeek()).isEqualTo(
            LocalDate(2022, 11, 20)
        )
    }

    @Test
    fun `Correct first Day of month`() {
        assertThat(testDate.firstDayOfMonth()).isEqualTo(
            LocalDate(2022, 11, 1)
        )
    }

    @Test
    fun `Correct first Day of year`() {
        assertThat(testDate.firstDayOfYear()).isEqualTo(
            LocalDate(2022, 1, 1)
        )
    }

    @Test
    fun `Correct first Day of week of sunday is itself`() {
        val testDate = LocalDate(2022, 11, 20)

        assertThat(testDate.firstDayOfWeek()).isEqualTo(testDate)
    }

    @Test
    fun `Correct first Day of Month of the first day of Month is itself`() {
        val testDate = LocalDate(2022, 11, 1)

        assertThat(testDate.firstDayOfMonth()).isEqualTo(testDate)
    }

    @Test
    fun `Correct first Day of Year of the first day of Year is itself`() {
        val testDate = LocalDate(2022, 1, 1)

        assertThat(testDate.firstDayOfYear()).isEqualTo(testDate)
    }

    @Test
    fun `Correct last Day of week`() {
        assertThat(testDate.lastDayOfWeek()).isEqualTo(
            LocalDate(2022, 11, 26)
        )
    }

    @Test
    fun `Correct last Day of month`() {
        assertThat(testDate.lastDayOfMonth()).isEqualTo(
            LocalDate(2022, 11, 30)
        )
    }

    @Test
    fun `Correct last Day of year`() {
        assertThat(testDate.lastDayOfYear()).isEqualTo(
            LocalDate(2022, 12, 31)
        )
    }

    @Test
    fun `Correct last Day of week of Saturday is itself`() {
        val testDate = LocalDate(2022, 11, 19)

        assertThat(testDate.lastDayOfWeek()).isEqualTo(testDate)
    }

    @Test
    fun `Correct last Day of Month of the last day of Month is itself`() {
        val testDate = LocalDate(2022, 11, 30)

        assertThat(testDate.lastDayOfMonth()).isEqualTo(testDate)
    }

    @Test
    fun `Correct last Day of Year of the last day of Year is itself`() {
        val testDate = LocalDate(2022, 12, 31)

        assertThat(testDate.lastDayOfMonth()).isEqualTo(testDate)
    }

    @Test
    fun `Difference by days calculated correctly in same day`() {
        val date1 = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val date2 = timeFromSunday().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val difference = date1.minus(date2, DateTimeUnit.DAY)

        assertThat(difference).isEqualTo(0)
    }

    @Test
    fun `Difference by days calculated correctly`() {
        val date1 = LocalDate(2022, 12, 31)
        val date2 = LocalDate(2022, 11, 30)
        val difference = date1.minus(date2, DateTimeUnit.DAY)

        assertThat(difference).isEqualTo(31)
    }

    @Test
    fun `Difference by weeks calculated correctly`() {
        val date1 = LocalDate(2022, 12, 31)
        val date2 = LocalDate(2022, 11, 30)
        val difference = date1.minus(date2, DateTimeUnit.WEEK)

        assertThat(difference).isEqualTo(4)
    }

    @Test
    fun `Difference by months calculated correctly`() {
        val date1 = LocalDate(2022, 12, 31)
        val date2 = LocalDate(2020, 11, 30)
        val difference = date1.minus(date2, DateTimeUnit.MONTH)

        assertThat(difference).isEqualTo(25)
    }

    @Test
    fun `Difference by years calculated correctly`() {
        val date1 = LocalDate(2022, 12, 31)
        val date2 = LocalDate(2019, 11, 30)
        val difference = date1.minus(date2, DateTimeUnit.YEAR)

        assertThat(difference).isEqualTo(3)
    }
}