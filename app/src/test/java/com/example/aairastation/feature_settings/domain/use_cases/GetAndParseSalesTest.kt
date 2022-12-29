package com.example.aairastation.feature_settings.domain.use_cases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.aairastation.MainCoroutineRule
import com.example.aairastation.core.timeFromSunday
import com.example.aairastation.data.repository.TestRepository
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.OrderDetail
import com.example.aairastation.feature_settings.domain.model.FoodStatsItem
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetAndParseSalesTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: TestRepository

    private fun mockDetail(
        id: Long,
        completed: Boolean,
    ) = OrderDetail(
        order = FoodOrder.example.copy(
            orderId = id,
            createdAt = timeFromSunday().toEpochMilliseconds()
        ),
        food = Food(id, "$id"),
        amount = 1,
        completed = completed,
    )


    private val details = listOf(
        mockDetail(id = 1, completed = false),
        mockDetail(id = 1, completed = false),

        mockDetail(id = 2, completed = true),
        mockDetail(id = 2, completed = false),

        mockDetail(id = 3, completed = true),
        mockDetail(id = 3, completed = true),
    )

    private lateinit var filteredDetails: List<FoodStatsItem>

    @Before
    fun setup() {
        repository = TestRepository()

        runBlocking {
            details.onEach { repository.insertOrderDetail(it) }
        }

        val useCases = SettingsUseCases.create(repository = repository)

        runBlocking {
            filteredDetails = useCases.getAndParseSales().first()
        }
    }

    @Test
    fun `Fully complete order included`() = runTest {
        assertThat(filteredDetails).contains(
            FoodStatsItem.fromDetail(
                mockDetail(id = 3, completed = true)
            )
        )
        assertThat(filteredDetails).contains(
            FoodStatsItem.fromDetail(
                mockDetail(id = 3, completed = true)
            )
        )
    }

    @Test
    fun `Incomplete order not included`() = runTest {
        assertThat(filteredDetails).doesNotContain(
            FoodStatsItem.fromDetail(
                mockDetail(id = 1, completed = false)
            )
        )
        assertThat(filteredDetails).doesNotContain(
            FoodStatsItem.fromDetail(
                mockDetail(id = 1, completed = false)
            )
        )
    }

    @Test
    fun `Partially complete order not included`() = runTest {
        assertThat(filteredDetails).doesNotContain(
            FoodStatsItem.fromDetail(
                mockDetail(id = 2, completed = true)
            )
        )
        assertThat(filteredDetails).doesNotContain(
            FoodStatsItem.fromDetail(
                mockDetail(id = 2, completed = false)
            )
        )
    }
}