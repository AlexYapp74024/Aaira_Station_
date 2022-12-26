package com.example.aairastation.feature_order.presentation.order_details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OrderDetailViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()

    private lateinit var repository: TestRepository
    private lateinit var viewModel: OrderDetailViewModel

    @Before
    fun setup() {
        repository = TestRepository()

        val useCases = OrderUseCases.create(
            repository = repository
        )

        viewModel = OrderDetailViewModel(useCases)

        runBlocking {
            val orderId1 = 1L
            repository.insertOrder(mockOrder(orderId1))
            repository.insertOrderDetail(mockDetail(1, orderId1, true))
            repository.insertOrderDetail(mockDetail(2, orderId1, true))
            repository.insertOrderDetail(mockDetail(3, orderId1, true))

            val orderId2 = 2L
            repository.insertOrder(mockOrder(orderId2))
            repository.insertOrderDetail(mockDetail(1, orderId2, false))
            repository.insertOrderDetail(mockDetail(2, orderId2, true))
            repository.insertOrderDetail(mockDetail(3, orderId2, true))
        }
    }

    private fun mockOrder(id: Long) = FoodOrder.example.copy(orderId = id)
    private fun mockDetail(id: Long, orderId: Long, completed: Boolean) =
        OrderDetail(detailId = id, mockOrder(orderId), Food.example, 1, completed = completed)

    // Retrieve orders and Details
    @Test
    fun `Orders are retrieved from repository with ID`() = runTest {
        val orderId = 2L
        viewModel.retrieveOrders(orderId)

        dispatcher.scheduler.advanceUntilIdle()
        val vmOrders = viewModel.order.first()
        assertThat(vmOrders).isEqualTo(mockOrder(orderId))

        val vmDetails = viewModel.details.first()
        assertThat(vmDetails).isEqualTo(
            listOf(
                mockDetail(1, orderId, false),
                mockDetail(2, orderId, true),
                mockDetail(3, orderId, true),
            )
        )
    }

    @Test
    fun `Orders are not changed with non existing ID`() = runTest {
        val orderId = 3L
        viewModel.retrieveOrders(orderId)

        val vmOrders = viewModel.order.first()

        assertThat(vmOrders).isEqualTo(null)

        val vmDetails = viewModel.details.first()
        assertThat(vmDetails).isEmpty()
    }

    // Check Out Screen
    // Submit and add orders
    @Test
    fun `Submits now order to database`() = runTest {
        val orderId = 3L
        val order = mockOrder(orderId)
        val details = listOf(
            mockDetail(1, orderId, false),
            mockDetail(2, orderId, true),
            mockDetail(3, orderId, true),
        )

        viewModel.newOrder(order, details)
        dispatcher.scheduler.advanceUntilIdle()

        val vmOrders = viewModel.order.first()
        assertThat(vmOrders).isEqualTo(order)

        val vmDetails = viewModel.details.first()
        assertThat(vmDetails).isEqualTo(details)
    }

    // Ongoing Order Detail Screen
    // can cancel order
    // can complete order
}