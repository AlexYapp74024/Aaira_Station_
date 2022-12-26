package com.example.aairastation.feature_order.presentation.order_menu

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.aairastation.MainCoroutineRule
import com.example.aairastation.data.repository.TestImageRepository
import com.example.aairastation.data.repository.TestRepository
import com.example.aairastation.feature_menu.domain.MenuUseCases
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class OrderMenuListViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: TestRepository
    private lateinit var viewModel: OrderMenuListViewModel

    @Before
    fun setup() {
        repository = TestRepository()

        runBlocking {
            repository.insertFoodCategory(mockCategory(1))
            repository.insertFood(mockFood(1, 1))
            repository.insertFood(mockFood(2, 1))

            repository.insertFoodCategory(mockCategory(2))
            repository.insertFood(mockFood(1, 2))
            repository.insertFood(mockFood(2, 2))

            repository.insertFoodCategory(mockCategory(null))
            repository.insertFood(mockFood(1, null))
            repository.insertFood(mockFood(2, null))
        }

        val useCases = MenuUseCases.create(
            repository = repository,
            imageRepository = TestImageRepository()
        )

        viewModel = OrderMenuListViewModel(useCases)

        viewModel.refreshItem()
    }

    private fun mockCategory(id: Long?) =
        if (id == null)
            FoodCategory.noCategory
        else
            FoodCategory.example.copy(categoryId = id)

    private fun mockFood(id: Long, categoryId: Long?) =
        Food(id, "Item $id", mockCategory(categoryId))

    @Test
    fun `Food counter initially empty`() {
        assertThat(viewModel.foodQuantity.value).isEmpty()
    }

    // strip out all details, discard the rest
    private suspend fun getAllFood() =
        viewModel.itemsAndCategories.first().map { (_, itemsAndBitmap) ->
            itemsAndBitmap.keys.toList()
        }.flatten()

    @Test
    fun `Item list is not empty`() = runTest {
        assertThat(viewModel.itemsAndCategories.first()).isNotEmpty()
    }

    @Test
    fun `Food incremented once`() = runTest {
        val targetFood = getAllFood().first()
        viewModel.incrementFood(targetFood)

        assertThat(viewModel.foodQuantity.value).containsEntry(
            targetFood, 1
        )
    }

    @Test
    fun `Food incremented 5 times`() = runTest {
        val targetFood = getAllFood().first()
        repeat(5) {
            viewModel.incrementFood(targetFood)
        }

        assertThat(viewModel.foodQuantity.value).containsEntry(
            targetFood, 5
        )
    }

    @Test
    fun `Food decremented becomes 0`() = runTest {
        val targetFood = getAllFood().first()
        repeat(5) {
            viewModel.incrementFood(targetFood)
        }

        viewModel.decrementFood(targetFood)

        assertThat(viewModel.foodQuantity.value).doesNotContainKey(targetFood)
    }
}