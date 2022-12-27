package com.example.aairastation.feature_order.presentation.order_details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.aairastation.data.data_source.MainDatabase
import com.example.aairastation.data.repository.MainRepositoryImpl
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.example.aairastation.feature_order.domain.use_case.OrderUseCases
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class OrderDetailViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MainDatabase
    private lateinit var repository: MainRepositoryImpl
    private lateinit var viewModel: OrderDetailViewModel

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MainDatabase::class.java
        ).allowMainThreadQueries().build()
        repository = MainRepositoryImpl(database.dao)

        val useCases = OrderUseCases.create(repository = repository)
        viewModel = OrderDetailViewModel(useCases)
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
    fun everythingInitializedCorrectly() = runTest {
        val vmDetails = viewModel.details.first()
        val vmOrders = viewModel.order.first()

        assertThat(vmDetails).isEmpty()
        assertThat(vmOrders).isNull()
    }

    @Test
    fun detailsParsedCorrectly() = runTest {
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