package com.example.aairastation.data.data_source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.*
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class MainDatabaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MainDatabase
    private lateinit var dao: MainDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MainDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.dao
    }

    @After
    fun teardown() {
        database.close()
    }

    // Relations
    @Test
    fun getFoodCategoryWithFood(categoryID: Int) = runTest {

    }

    @Test
    fun getFoodWithOrderDetails(foodID: Int) = runTest {

    }

    @Test
    fun getOrderWithOrderDetail(orderID: Int) = runTest {

    }
}