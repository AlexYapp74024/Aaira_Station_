package com.example.aairastation.feature_order.presentation.order_list

import com.example.aairastation.MainCoroutineRule
import com.example.aairastation.data.repository.TestRepository
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.OrderDetail
import com.example.aairastation.feature_order.domain.use_case.OrderUseCases
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OrderListViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var repository: TestRepository
    private lateinit var viewModel: OrderListViewModel

    @Before
    fun setup() {
        repository = TestRepository()

        val useCases = OrderUseCases.create(
            repository = repository
        )

        viewModel = OrderListViewModel(useCases)

        runBlocking {
            repository.insertOrder(mockOrder(1))
            repository.insertOrderDetail(mockDetail(1, 1, true))
            repository.insertOrderDetail(mockDetail(2, 1, true))
            repository.insertOrderDetail(mockDetail(3, 1, true))

            repository.insertOrder(mockOrder(2))
            repository.insertOrderDetail(mockDetail(1, 2, true))
            repository.insertOrderDetail(mockDetail(2, 2, false))
            repository.insertOrderDetail(mockDetail(3, 2, true))

            repository.insertOrder(mockOrder(3))
            repository.insertOrderDetail(mockDetail(1, 3, false))
            repository.insertOrderDetail(mockDetail(2, 3, false))
            repository.insertOrderDetail(mockDetail(3, 3, true))
        }
    }

    private fun mockOrder(id: Long) = FoodOrder.example.copy(orderId = id)
    private fun mockDetail(id: Long, orderId: Long, completed: Boolean) =
        OrderDetail(detailId = id, mockOrder(orderId), Food.example, 1, completed = completed)

    @Test
    fun `Old orders are separated correctly `() = runTest {
        val expected = mapOf(
            mockOrder(1) to listOf(
                mockDetail(1, 1, true),
                mockDetail(2, 1, true),
                mockDetail(3, 1, true),
            ),
        )

        val result = viewModel.completedOrders.first()
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `Current orders are separated correctly `() = runTest {
        val expected = mapOf(
            mockOrder(2) to listOf(
                mockDetail(1, 2, true),
                mockDetail(2, 2, false),
                mockDetail(3, 2, true),
            ),
            mockOrder(3) to listOf(
                mockDetail(1, 3, false),
                mockDetail(2, 3, false),
                mockDetail(3, 3, true),
            ),
        )

        val result = viewModel.currentOrders.first()
        assertThat(result).isEqualTo(expected)
    }
}