package com.example.aairastation.feature_settings.presentation.sales_report

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.aairastation.MainCoroutineRule
import com.example.aairastation.core.lastDayOf
import com.example.aairastation.core.minus
import com.example.aairastation.core.timeFromNow
import com.example.aairastation.data.repository.TestRepository
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.OrderDetail
import com.example.aairastation.feature_settings.domain.model.TimeGrouping
import com.example.aairastation.feature_settings.domain.use_cases.SettingsUseCases
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SalesReportViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: TestRepository
    private lateinit var useCases: SettingsUseCases
    private lateinit var viewModel: SalesReportViewModel

    private fun mockDetail(
        food: Food,
        creationTime: Instant,
        completed: Boolean = true
    ) = OrderDetail(
        order = FoodOrder.example.copy(
            createdAt = creationTime.toEpochMilliseconds()
        ),
        food = food,
        amount = 1,
        completed = completed,
    )

    private val food1 = Food(foodName = "Food 1", priceInCents = 1000)

    private val details = listOf(
        // 2 incomplete orders
        mockDetail(food1, timeFromNow(daysAgo = 1), completed = false),
        mockDetail(food1, timeFromNow(daysAgo = 1), completed = false),

        // 6 orders this week
        mockDetail(food1, timeFromNow(daysAgo = 1)),
        mockDetail(food1, timeFromNow(daysAgo = 1)),

        mockDetail(food1, timeFromNow(daysAgo = 2)),
        mockDetail(food1, timeFromNow(daysAgo = 2)),

        mockDetail(food1, timeFromNow(daysAgo = 4)),
        mockDetail(food1, timeFromNow(daysAgo = 4)),

        // 3 orders last week
        mockDetail(food1, timeFromNow(weeksAgo = 1)),
        mockDetail(food1, timeFromNow(weeksAgo = 2)),
        mockDetail(food1, timeFromNow(weeksAgo = 3)),

        // 3 orders this year
        mockDetail(food1, timeFromNow(monthsAgo = 1)),
        mockDetail(food1, timeFromNow(monthsAgo = 2)),
        mockDetail(food1, timeFromNow(monthsAgo = 4)),

        // 3 orders last year
        mockDetail(food1, timeFromNow(yearsAgo = 1)),
        mockDetail(food1, timeFromNow(yearsAgo = 2)),
        mockDetail(food1, timeFromNow(yearsAgo = 4)),
    )

    @Before
    fun setup() {
        repository = TestRepository()

        runBlocking {
            details.onEach { repository.insertOrderDetail(it) }
        }

        useCases = SettingsUseCases.create(repository = repository)

        viewModel = SalesReportViewModel(useCases)
    }

    @Test
    fun `Default is sort by day`() {
        val timeGrouping = viewModel.timeGrouping.value
        assertThat(timeGrouping).isEqualTo(TimeGrouping.Daily)
    }

    private suspend fun assertViewModelContainsEntries(vararg time_amount: Pair<Instant, Int>) {
        val timeGrouping = viewModel.timeGrouping.value
        val timeZone = TimeZone.currentSystemDefault()

        val expectedMap = time_amount.map { (instant, amount) ->
            val date = instant.toLocalDateTime(timeZone).date
            val period = Clock.System.todayIn(timeZone)
                .lastDayOf(timeGrouping.dateTimeUnit)
                .minus(date, timeGrouping.dateTimeUnit)

            val price = amount * food1.priceInCents

            period to price
        }.toMap()

        val entries = viewModel.entries.first()

        assertThat(entries).isNotEmpty()
        entries.forEach {
            assertThat(expectedMap).containsEntry(
                it.number, it.total
            )
        }
    }

    @Test
    fun `sales summed by days`() = runTest {
        assertViewModelContainsEntries(
            // 6 orders this week
            timeFromNow(daysAgo = 1) to 2,
            timeFromNow(daysAgo = 2) to 2,
            timeFromNow(daysAgo = 4) to 2,

            // 3 orders this month
            timeFromNow(weeksAgo = 1) to 1,
            timeFromNow(weeksAgo = 2) to 1,
            timeFromNow(weeksAgo = 3) to 1,

            // 3 orders this year
            timeFromNow(monthsAgo = 1) to 1,
            timeFromNow(monthsAgo = 2) to 1,
            timeFromNow(monthsAgo = 4) to 1,

            // 3 orders last years
            timeFromNow(yearsAgo = 1) to 1,
            timeFromNow(yearsAgo = 2) to 1,
            timeFromNow(yearsAgo = 4) to 1,
        )
    }

    @Test
    fun `sales summed by weeks`() = runTest {
        viewModel.setTimeGrouping(TimeGrouping.Weekly)
        assertViewModelContainsEntries(
            // 6 orders this weeks
            timeFromNow() to 6,

            // 3 orders this month
            timeFromNow(weeksAgo = 1) to 1,
            timeFromNow(weeksAgo = 2) to 1,
            timeFromNow(weeksAgo = 3) to 1,

            // 3 orders this year
            timeFromNow(monthsAgo = 1) to 1,
            timeFromNow(monthsAgo = 2) to 1,
            timeFromNow(monthsAgo = 4) to 1,

            // 3 orders last years
            timeFromNow(yearsAgo = 1) to 1,
            timeFromNow(yearsAgo = 2) to 1,
            timeFromNow(yearsAgo = 4) to 1,
        )
    }

    @Test
    fun `sales summed by months`() = runTest {
        viewModel.setTimeGrouping(TimeGrouping.Monthly)
        assertViewModelContainsEntries(
            // 6 orders this month
            timeFromNow() to 9,

            // 3 orders this year
            timeFromNow(monthsAgo = 1) to 1,
            timeFromNow(monthsAgo = 2) to 1,
            timeFromNow(monthsAgo = 4) to 1,

            // 3 orders last years
            timeFromNow(yearsAgo = 1) to 1,
            timeFromNow(yearsAgo = 2) to 1,
            timeFromNow(yearsAgo = 4) to 1,
        )
    }

    @Test
    fun `sales summed by years`() = runTest {
        viewModel.setTimeGrouping(TimeGrouping.Yearly)
        assertViewModelContainsEntries(
            // 12 orders this year
            timeFromNow() to 12,

            // 3 orders last year
            timeFromNow(yearsAgo = 1) to 1,
            timeFromNow(yearsAgo = 2) to 1,
            timeFromNow(yearsAgo = 4) to 1,
        )
    }
}
