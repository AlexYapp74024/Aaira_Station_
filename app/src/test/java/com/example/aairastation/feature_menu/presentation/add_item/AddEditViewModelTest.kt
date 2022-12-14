package com.example.aairastation.feature_menu.presentation.add_item

import app.cash.turbine.test
import com.example.aairastation.MainCoroutineRule
import com.example.aairastation.data.repository.TestImageRepository
import com.example.aairastation.data.repository.TestRepository
import com.example.aairastation.feature_menu.domain.MenuUseCases
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.example.aairastation.feature_menu.domain.model.formattedPrice
import com.example.aairastation.feature_menu.presentation.add_edit.AddEditViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddEditViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var repository: TestRepository
    private lateinit var viewModel: AddEditViewModel

    @Before
    fun setup() {
        repository = TestRepository()

        val useCases = MenuUseCases.create(
            imageRepository = TestImageRepository(),
            repository = repository
        )

        viewModel = AddEditViewModel(useCases)
    }

    @Test
    fun `Enter Price to updates State`() = runTest {
        val inputPrice = "8.00"
        val expectedOutput = "RM 8.00"

        inputPrice.toDoubleOrNull()?.let {
            val priceInCents = (it * 100).toInt()
            viewModel.updateItemState(Food(priceInCents = priceInCents))
        }

        viewModel.item.test {
            assertThat(awaitItem().item.formattedPrice).isEqualTo(expectedOutput)
        }
    }

    @Test
    fun `Empty category list contains 'No category'`() = runTest {
        val expected = listOf(FoodCategory.noCategory)
        val categories = viewModel.categories.value

        assertThat(categories).isEqualTo(expected)
    }

    @Test
    fun `Add new category updates item category`() = runTest {
        val newCategory = FoodCategory(categoryId = 1, categoryName = "Rice")
        viewModel.addAndSetNewCategory("Rice")

        val food = viewModel.item.value.item
        assertThat(food.category).isEqualTo(newCategory)

        val categories = repository.getAllFoodCategory().first()
        assertThat(categories).contains(newCategory)
    }
}