package com.example.aairastation.feature_settings.presentation.top_seller

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.aairastation.MainCoroutineRule
import com.example.aairastation.core.timeFromNow
import com.example.aairastation.data.repository.TestRepository
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.OrderDetail
import com.example.aairastation.feature_order.domain.use_case.OrderUseCases
import com.example.aairastation.feature_settings.domain.model.Grouping
import com.example.aairastation.feature_settings.domain.model.TimeGrouping
import com.example.aairastation.feature_settings.domain.use_cases.ParseOrderDetails
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
class TopSellerViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: TestRepository
    private lateinit var viewModel: TopSellerViewModel

    private fun mockDetail(
        food: Food,
        amount: Int,
        creationTime: Instant,
    ) = OrderDetail(
        order = FoodOrder.example.copy(
            createdAt = creationTime.toEpochMilliseconds()
        ),
        food = food,
        amount = amount,
    )

    // food1 always wins out in price
    // food2 always wins in quantity
    private val food1 = Food(foodName = "Food 1", priceInCents = 1000)
    private val food2 = Food(foodName = "Food 2", priceInCents = 100)

    private val details = listOf(
        // 3 orders today
        mockDetail(food1, 1, timeFromNow()),
        mockDetail(food2, 2, timeFromNow()),
        mockDetail(food2, 2, timeFromNow()),

        // 3 orders yesterday
        mockDetail(food1, 1, timeFromNow(daysAgo = 1)),
        mockDetail(food2, 2, timeFromNow(daysAgo = 1)),
        mockDetail(food2, 2, timeFromNow(daysAgo = 1)),

        // 3 orders last week
        mockDetail(food1, 1, timeFromNow(weeksAgo = 1)),
        mockDetail(food2, 2, timeFromNow(weeksAgo = 1)),
        mockDetail(food2, 2, timeFromNow(weeksAgo = 1)),

        // 3 orders last month
        mockDetail(food1, 1, timeFromNow(monthsAgo = 1)),
        mockDetail(food2, 2, timeFromNow(monthsAgo = 1)),
        mockDetail(food2, 2, timeFromNow(monthsAgo = 1)),

        // 3 orders last year
        mockDetail(food1, 1, timeFromNow(yearsAgo = 1)),
        mockDetail(food2, 2, timeFromNow(yearsAgo = 1)),
        mockDetail(food2, 2, timeFromNow(yearsAgo = 1)),
    )

    @Before
    fun setup() {
        repository = TestRepository()

        runBlocking {
            details.onEach { repository.insertOrderDetail(it) }
        }

        val useCases = OrderUseCases.create(repository = repository)

        viewModel = TopSellerViewModel(useCases)
    }

    @Test
    fun `Default is sort by day`() {
        val filter = viewModel.timeGrouping.value
        assertThat(filter).isEqualTo(TimeGrouping.Daily)
    }

    private suspend fun assertDetailsContain(vararg detail: OrderDetail) {
        val details = viewModel.filtered.first()
        assertThat(details).isNotEmpty()

        val expectedList = ParseOrderDetails()(detail.toList())
        assertThat(details).isEqualTo(expectedList)
    }

    @Test
    fun `Default is today's orders`() = runTest {
        assertDetailsContain(
            mockDetail(food1, 1, timeFromNow()),
            mockDetail(food2, 2, timeFromNow()),
            mockDetail(food2, 2, timeFromNow()),
        )
    }

    @Test
    fun `Get Yesterday's orders`() = runTest {
        viewModel.shiftTimeBackward()

        assertDetailsContain(
            mockDetail(food1, 1, timeFromNow(daysAgo = 1)),
            mockDetail(food2, 2, timeFromNow(daysAgo = 1)),
            mockDetail(food2, 2, timeFromNow(daysAgo = 1)),
        )
    }

    @Test
    fun `Get This week's orders`() = runTest {
        viewModel.setTimeGrouping( TimeGrouping.Weekly)

        assertDetailsContain(
            // 3 orders today
            mockDetail(food1, 1, timeFromNow()),
            mockDetail(food2, 2, timeFromNow()),
            mockDetail(food2, 2, timeFromNow()),

            // 3 orders yesterday
            mockDetail(food1, 1, timeFromNow(daysAgo = 1)),
            mockDetail(food2, 2, timeFromNow(daysAgo = 1)),
            mockDetail(food2, 2, timeFromNow(daysAgo = 1)),
        )
    }

    @Test
    fun `Get Last week's orders`() = runTest {
        viewModel.setTimeGrouping( TimeGrouping.Weekly)
        viewModel.shiftTimeBackward()

        assertDetailsContain(
            // 3 orders last week
            mockDetail(food1, 1, timeFromNow(weeksAgo = 1)),
            mockDetail(food2, 2, timeFromNow(weeksAgo = 1)),
            mockDetail(food2, 2, timeFromNow(weeksAgo = 1)),
        )
    }

    @Test
    fun `Get This month's orders`() = runTest {
        viewModel.setTimeGrouping( TimeGrouping.Monthly)

        assertDetailsContain(
            // 3 orders today
            mockDetail(food1, 1, timeFromNow()),
            mockDetail(food2, 2, timeFromNow()),
            mockDetail(food2, 2, timeFromNow()),

            // 3 orders yesterday
            mockDetail(food1, 1, timeFromNow(daysAgo = 1)),
            mockDetail(food2, 2, timeFromNow(daysAgo = 1)),
            mockDetail(food2, 2, timeFromNow(daysAgo = 1)),

            // 3 orders last week
            mockDetail(food1, 1, timeFromNow(weeksAgo = 1)),
            mockDetail(food2, 2, timeFromNow(weeksAgo = 1)),
            mockDetail(food2, 2, timeFromNow(weeksAgo = 1)),
        )
    }

    @Test
    fun `Get Last month's orders`() = runTest {
        viewModel.setTimeGrouping( TimeGrouping.Monthly)
        viewModel.shiftTimeBackward()

        assertDetailsContain(
            // 3 orders last month
            mockDetail(food1, 1, timeFromNow(monthsAgo = 1)),
            mockDetail(food2, 2, timeFromNow(monthsAgo = 1)),
            mockDetail(food2, 2, timeFromNow(monthsAgo = 1)),
        )
    }

    @Test
    fun `Get This year's orders`() = runTest {
        viewModel.setTimeGrouping( TimeGrouping.Yearly)

        assertDetailsContain(
            // 3 orders today
            mockDetail(food1, 1, timeFromNow()),
            mockDetail(food2, 2, timeFromNow()),
            mockDetail(food2, 2, timeFromNow()),

            // 3 orders yesterday
            mockDetail(food1, 1, timeFromNow(daysAgo = 1)),
            mockDetail(food2, 2, timeFromNow(daysAgo = 1)),
            mockDetail(food2, 2, timeFromNow(daysAgo = 1)),

            // 3 orders last week
            mockDetail(food1, 1, timeFromNow(weeksAgo = 1)),
            mockDetail(food2, 2, timeFromNow(weeksAgo = 1)),
            mockDetail(food2, 2, timeFromNow(weeksAgo = 1)),

            // 3 orders last month
            mockDetail(food1, 1, timeFromNow(monthsAgo = 1)),
            mockDetail(food2, 2, timeFromNow(monthsAgo = 1)),
            mockDetail(food2, 2, timeFromNow(monthsAgo = 1)),
        )
    }

    @Test
    fun `Get Last year's orders`() = runTest {
        viewModel.setTimeGrouping(TimeGrouping.Yearly)
        viewModel.shiftTimeBackward()

        assertDetailsContain(
            // 3 orders last year
            mockDetail(food1, 1, timeFromNow(yearsAgo = 1)),
            mockDetail(food2, 2, timeFromNow(yearsAgo = 1)),
            mockDetail(food2, 2, timeFromNow(yearsAgo = 1)),
        )
    }

    @Test
    fun `Time can be shifted forward`() = runTest {
        viewModel.shiftTimeBackward()
        viewModel.shiftTimeForward()

        assertDetailsContain(
            mockDetail(food1, 1, timeFromNow()),
            mockDetail(food2, 2, timeFromNow()),
            mockDetail(food2, 2, timeFromNow()),
        )
    }

    @Test
    fun `Time cannot be shifted forward beyond today`() = runTest {
        viewModel.shiftTimeBackward()
        viewModel.shiftTimeForward()
        viewModel.shiftTimeForward()
        viewModel.shiftTimeForward()

        assertDetailsContain(
            mockDetail(food1, 1, timeFromNow()),
            mockDetail(food2, 2, timeFromNow()),
            mockDetail(food2, 2, timeFromNow()),
        )
    }

    @Test
    fun `Default is Sum of price`() = runTest {
        val grouping = viewModel.grouping.value

        assertThat(grouping).isEqualTo(Grouping.Price)
    }

    @Test
    fun `Details are grouped by sum`() = runTest {
        val grouped = viewModel.items.first()

        assertThat(grouped).isEqualTo(
            listOf(
                food1.foodName to 1000,
                food2.foodName to 400,
            )
        )
    }

    @Test
    fun `Details are grouped by amount`() = runTest {
        viewModel.setGrouping( Grouping.Amount)
        val grouped = viewModel.items.first()

        assertThat(grouped).isEqualTo(
            listOf(
                food2.foodName to 4,
                food1.foodName to 1,
            )
        )
    }
}