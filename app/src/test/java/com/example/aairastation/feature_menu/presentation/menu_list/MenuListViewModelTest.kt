package com.example.aairastation.feature_menu.presentation.menu_list

import com.example.aairastation.MainCoroutineRule
import com.example.aairastation.data.repository.TestImageRepository
import com.example.aairastation.data.repository.TestRepository
import com.example.aairastation.feature_menu.domain.MenuUseCases
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MenuListViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var repository: TestRepository
    private lateinit var viewModel: MenuListViewModel

    @Before
    fun setup() {
        repository = TestRepository()

        val useCases = MenuUseCases.create(
            imageRepository = TestImageRepository(),
            repository = repository
        )

        viewModel = MenuListViewModel(useCases)
    }

    @Test
    fun `Flow Test Normal Items List`() = runTest {
        val expectedMap = mapOf(
            mockCategory(1) to listOf(
                mockFood(1, 1),
                mockFood(2, 1),
            ),
            mockCategory(2) to listOf(
                mockFood(1, 2),
                mockFood(2, 2),
            )
        )

        testViewModelItemState(expectedMap)
    }

    @Test
    fun `Flow Test Include Food Without Category `() = runTest {
        val expectedMap = mapOf(
            mockCategory(1) to listOf(
                mockFood(1, 1),
                mockFood(2, 1),
            ),
            mockCategory(2) to listOf(
                mockFood(1, 2),
                mockFood(2, 2),
            ),
            mockCategory(null) to listOf(
                mockFood(1, null),
                mockFood(2, null),
            )
        )

        testViewModelItemState(expectedMap)
    }

    private fun mockCategory(id: Long?) =
        if (id == null)
            FoodCategory.noCategory
        else
            FoodCategory.example.copy(categoryId = id)

    private fun mockFood(id: Long, categoryId: Long?) =
        Food(id, "Item $id", mockCategory(categoryId))

    private suspend fun addMapToRepository(map: Map<FoodCategory, List<Food>>) {
        map.onEach { (category, foods) ->
            repository.insertFoodCategory(category)
            foods.onEach { food ->
                repository.insertFood(food)
            }
        }
    }


    private suspend fun testViewModelItemState(expectedMap: Map<FoodCategory, List<Food>>) {
        addMapToRepository(expectedMap)

        viewModel.refreshItems()
        val vmMap = viewModel.itemsAndCategories.first()

        // scrub away all bitmap flows
        val resultMap: Map<FoodCategory, List<Food>> = vmMap.map { (category, items) ->
            val forageItems = items.map { (forageItem, _) -> forageItem }
            category to forageItems
        }.toMap()

        assertThat(resultMap).isEqualTo(expectedMap)
    }
}