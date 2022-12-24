package com.example.aairastation.feature_menu.presentation.menu_list

import app.cash.turbine.test
import com.example.aairastation.MainCoroutineRule
import com.example.aairastation.data.repository.TestImageRepository
import com.example.aairastation.data.repository.TestRepository
import com.example.aairastation.feature_menu.domain.MenuUseCases
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.example.aairastation.feature_menu.presentation.view_models.MenuListViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

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
//
//    @Test
//    fun `Flow Test Normal Items List`() = runTest {
//        val expectedMap = mapOf(
//            FoodCategory(1) to listOf(
//                mockFood(1, 1),
//                mockFood(2, 1),
//            ),
//            FoodCategory(2) to listOf(
//                mockFood(1, 2),
//                mockFood(2, 2),
//            )
//        )
//
//        testViewModelItemState(expectedMap)
//    }
//
//    @Test
//    fun `Flow Test Include Food Without Category `() = runTest {
//        val expectedMap = mapOf(
//            FoodCategory(1) to listOf(
//                mockFood(1, 1),
//                mockFood(2, 1),
//            ),
//            FoodCategory(2) to listOf(
//                mockFood(1, 2),
//                mockFood(2, 2),
//            ),
//            FoodCategory.noCategory to listOf(
//                mockFood(1, null),
//                mockFood(2, null),
//            )
//        )
//
//        testViewModelItemState(expectedMap)
//    }


    private fun mockFood(id: Long, category: FoodCategory?) =
        Food(id, "Item $id", category)

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

        viewModel.refreshItemsSuspend()
        viewModel.itemsAndCategories.test {
            val resultMap: Map<FoodCategory, List<Food>> = awaitItem().map { (category, items) ->
                val forageItems = items.map { (forageItem, _) -> forageItem }
                category to forageItems
            }.toMap()

            assertThat(resultMap).isEqualTo(expectedMap)
        }
    }
}