package com.example.aairastation.feature_order.presentation.order_details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.aairastation.MainCoroutineRule
import com.example.aairastation.data.repository.TestRepository
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.OrderDetail
import com.example.aairastation.feature_order.domain.use_case.OrderUseCases
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OrderDetailViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

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
            val orderId = 2L
            repository.insertOrder(mockOrder(orderId))
            repository.insertOrderDetail(mockDetail(4, orderId, false))
            repository.insertOrderDetail(mockDetail(5, orderId, false))
            repository.insertOrderDetail(mockDetail(6, orderId, true))
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
        val vmOrders = viewModel.order.first()
        val vmDetails = viewModel.details.first()

        assertThat(vmOrders).isEqualTo(mockOrder(orderId))
        assertThat(vmDetails).isEqualTo(
            listOf(
                mockDetail(4, orderId, false),
                mockDetail(5, orderId, false),
                mockDetail(6, orderId, true),
            )
        )
    }

    @Test
    fun `Orders are not changed with non existing ID`() = runTest {
        val orderId = 3L
        viewModel.retrieveOrders(orderId)

        val vmOrders = viewModel.order.first()
        val vmDetails = viewModel.details.first()

        assertThat(vmOrders).isEqualTo(null)
        assertThat(vmDetails).isEmpty()
    }

    // Check Out Screen
    // Submit and add orders
    @Test
    fun `Submits now order to database`() = runTest {
        val orderId = 3L
        val order = mockOrder(orderId)
        val details = listOf(
            mockDetail(7, orderId, false),
            mockDetail(8, orderId, false),
            mockDetail(9, orderId, true),
        )

        viewModel.updateAll(order, details)

        val vmOrders = viewModel.order.first()
        assertThat(vmOrders).isEqualTo(order)

        val vmDetails = viewModel.details.first()
        assertThat(vmDetails).isEqualTo(details)
    }

    // Ongoing Order Detail Screen
    // can complete order

    @Test
    fun `Set detail as completed changes state`() = runTest {
        val orderId = 2L
        viewModel.retrieveOrders(orderId)

        val vmDetails = viewModel.details.first()
        viewModel.updateDetail(vmDetails[1].copy(completed = true))

        val newVmDetails = viewModel.details.first()
        assertThat(newVmDetails).isEqualTo(
            listOf(
                mockDetail(4, orderId, false),
                mockDetail(5, orderId, true),
                mockDetail(6, orderId, true),
            )
        )
    }

    @Test
    fun `Save order updates repository`() = runTest {
        val orderId = 2L
        viewModel.retrieveOrders(orderId)

        val vmDetails = viewModel.details.first()
        val newDetailState = vmDetails[1].copy(completed = true)

        viewModel.updateDetail(newDetailState)
        viewModel.saveOrder()

        assertThat(repository.getAllOrderDetail().first()).contains(newDetailState)
    }

    @Test
    fun `Toggle incomplete order completes all details`() = runTest {
        val orderId = 2L
        viewModel.retrieveOrders(orderId)

        viewModel.toggleAllOrders()

        val vmDetails = viewModel.details.first()
        assertThat(vmDetails).isEqualTo(
            listOf(
                mockDetail(4, orderId, true),
                mockDetail(5, orderId, true),
                mockDetail(6, orderId, true),
            )
        )
    }

    private fun mockCategory(id: Long?) =
        if (id == null)
            FoodCategory.noCategory
        else
            FoodCategory.example.copy(categoryId = id)

    private fun mockFood(id: Long, categoryId: Long?) =
        Food(id, "Item $id", mockCategory(categoryId))

    private val format = Json { allowStructuredMapKeys = true }

    @Test
    fun `Details Parsed correctly`() = runTest {
        val foodQuantity = mapOf(
            mockFood(1, 1) to 1,
            mockFood(2, 1) to 2,
            mockFood(3, 2) to 2,
        )
        viewModel.parseFoodQuantity(format.encodeToString(foodQuantity))

        val vmDetails = viewModel.details.first()
        assertThat(vmDetails).isNotEmpty()
        vmDetails.forEach {
            assertThat(foodQuantity).containsEntry(
                it.food, it.amount
            )
        }
    }
}