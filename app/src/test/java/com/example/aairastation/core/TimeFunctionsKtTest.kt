package com.example.aairastation.core

import com.google.common.truth.Truth.assertThat
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import org.junit.Test

class TimeFunctionsKtTest {

    private val testDate = LocalDate(2022, Month.NOVEMBER, 24)

    @Test
    fun `Correct first Day of week`() {
        assertThat(testDate.firstDayOfWeek()).isEqualTo(
            LocalDate(2022, Month.NOVEMBER, 20)
        )
    }

    @Test
    fun `Correct first Day of month`() {
        assertThat(testDate.firstDayOfMonth()).isEqualTo(
            LocalDate(2022, Month.NOVEMBER, 1)
        )
    }

    @Test
    fun `Correct first Day of year`() {
        assertThat(testDate.firstDayOfYear()).isEqualTo(
            LocalDate(2022, Month.JANUARY, 1)
        )
    }

    @Test
    fun `Correct first Day of week of sunday is itself`() {
        val testDate = LocalDate(2022, Month.NOVEMBER, 20)

        assertThat(testDate.firstDayOfWeek()).isEqualTo(testDate)
    }

    @Test
    fun `Correct first Day of Month of the first day of Month is itself`() {
        val testDate = LocalDate(2022, Month.NOVEMBER, 1)

        assertThat(testDate.firstDayOfMonth()).isEqualTo(testDate)
    }

    @Test
    fun `Correct first Day of Year of the first day of Year is itself`() {
        val testDate = LocalDate(2022, Month.JANUARY, 1)

        assertThat(testDate.firstDayOfYear()).isEqualTo(testDate)
    }

    @Test
    fun `Correct last Day of week`() {
        assertThat(testDate.lastDayOfWeek()).isEqualTo(
            LocalDate(2022, Month.NOVEMBER, 26)
        )
    }

    @Test
    fun `Correct last Day of month`() {
        assertThat(testDate.lastDayOfMonth()).isEqualTo(
            LocalDate(2022, Month.NOVEMBER, 30)
        )
    }

    @Test
    fun `Correct last Day of year`() {
        assertThat(testDate.lastDayOfYear()).isEqualTo(
            LocalDate(2022, Month.DECEMBER, 31)
        )
    }

    @Test
    fun `Correct last Day of week of Saturday is itself`() {
        val testDate = LocalDate(2022, Month.NOVEMBER, 19)

        assertThat(testDate.lastDayOfWeek()).isEqualTo(testDate)
    }

    @Test
    fun `Correct last Day of Month of the last day of Month is itself`() {
        val testDate = LocalDate(2022, Month.NOVEMBER, 30)

        assertThat(testDate.lastDayOfMonth()).isEqualTo(testDate)
    }

    @Test
    fun `Correct last Day of Year of the last day of Year is itself`() {
        val testDate = LocalDate(2022, Month.DECEMBER, 31)

        assertThat(testDate.lastDayOfMonth()).isEqualTo(testDate)
    }
}