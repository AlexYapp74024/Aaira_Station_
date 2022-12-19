package com.example.aairastation.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.aairastation.data.data_source.MainDatabase
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.OrderDetail
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class MainRepositoryImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MainDatabase
    private lateinit var repositoryImpl: MainRepositoryImpl

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MainDatabase::class.java
        ).allowMainThreadQueries().build()
        repositoryImpl = MainRepositoryImpl(database.dao)
    }

    @Test
    fun getFoodCategoryWithFood() = runTest {
        val expectedMap = mapOf(
            FoodCategory(1, "Cat1") to listOf(
                Food(1, "Food1", 1),
                Food(2, "Food2", 1)
            )
        )

        expectedMap.onEach { (key, value) ->
            repositoryImpl.insertFoodCategory(key)
            value.onEach { repositoryImpl.insertFood(it) }
        }

        assertThat(repositoryImpl.getFoodCategoryWithFood(FoodCategory(1, "Cat1"))).isEqualTo(
            expectedMap
        )
    }

    @Test
    fun getFoodWithOrderDetail() = runTest {
        val expectedMap = mapOf(
            Food(1, "Food1") to listOf(
                OrderDetail(1, 1, 1),
                OrderDetail(2, 1, 1)
            )
        )

        expectedMap.onEach { (key, value) ->
            repositoryImpl.insertFood(key)
            value.onEach { repositoryImpl.insertOrderDetail(it) }
        }

        assertThat(repositoryImpl.getFoodWithOrderDetail(Food(1, "Food1"))).isEqualTo(expectedMap)
    }

    @Test
    fun getOrderWithOrderDetail() = runTest {
        val expectedMap = mapOf(
            FoodOrder(1, 1) to listOf(
                OrderDetail(1, 1, 1),
                OrderDetail(2, 1, 1)
            )
        )

        expectedMap.onEach { (key, value) ->
            repositoryImpl.insertOrder(key)
            value.onEach { repositoryImpl.insertOrderDetail(it) }
        }

        assertThat(repositoryImpl.getOrderWithOrderDetail(FoodOrder(1, 1))).isEqualTo(expectedMap)
    }
}