package com.example.aairastation.feature_menu.presentation.add_edit

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.example.aairastation.core.ui_util.BitmapWithDefault
import com.example.aairastation.core.ui_util.DefaultTopAppBar
import com.example.aairastation.core.ui_util.ExposedDropdownCanAddNewItem
import com.example.aairastation.core.ui_util.getImageFromInternalStorageLauncher
import com.example.aairastation.destinations.OrderMenuListScreenDestination
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.example.aairastation.feature_menu.domain.model.FoodWithImage
import com.example.aairastation.ui.theme.AairaStationTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

private lateinit var viewModel: AddEditViewModel
private var navigator: DestinationsNavigator = EmptyDestinationsNavigator
private var showDelete: Boolean = false

@Destination
@Composable
fun AddFoodScreen(
    navigatorIn: DestinationsNavigator,
) {
    viewModel = hiltViewModel()
    navigator = navigatorIn

    showDelete = true

    AddEditFoodScreenContent("New Item")
}

@Destination
@Composable
fun EditFoodScreen(
    foodID: Long,
    navigatorIn: DestinationsNavigator,
) {
    viewModel = hiltViewModel()
    navigator = navigatorIn

    showDelete = false

    viewModel.retrieveItem(foodID)

    AddEditFoodScreenContent("Edit Item")
}

@Composable
fun AddEditFoodScreenContent(title: String) {
    val food by viewModel.item.collectAsState(initial = FoodWithImage(Food()))
    val categories by viewModel.categories.collectAsState(initial = listOf())
    AddEditFoodScaffold(title = title) {
        AddEditFood(
            food = food.item,
            bitmap = food.bitmap,
            categories = categories,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun AddEditFood(
    food: Food,
    bitmap: Bitmap?,
    categories: List<FoodCategory>,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    val launcher = getImageFromInternalStorageLauncher {
        viewModel.updateItemBitmap(it)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val defaultModifier = Modifier.fillMaxWidth()

        BitmapWithDefault(
            bitmap = bitmap,
            contentDescription = "Change picture",
            modifier = Modifier
                .aspectRatio(2f)
                /**
                 * The user can tap on the item, which allows them change its image
                 */
                .clickable {
                    launcher.launch(
                        CropImageContractOptions(
                            null, CropImageOptions(
                                imageSourceIncludeCamera = true
                            )
                        )
                    )
                },
            contentScaleIfNotNull = ContentScale.Fit,
        )

        /**
         * Food name entry
         */
        OutlinedTextField(
            modifier = defaultModifier,
            value = food.foodName,
            onValueChange = {
                if (!it.contains('\n'))
                    viewModel.updateItemState(food.copy(foodName = it))
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            maxLines = 1,
            label = { Text(text = "Name") }
        )

        /**
         * item price
         */
        OutlinedTextField(
            modifier = defaultModifier,
            value = viewModel.priceState,
            onValueChange = { value ->
                viewModel.priceState = value
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number,
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            maxLines = 1,
            label = { Text(text = "Price") }
        )

        /**
         * item availability
         */
        Row(
            modifier = defaultModifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Available:", modifier = Modifier.weight(1f))

            Switch(
                checked = food.available,
                onCheckedChange = {
                    viewModel.updateItemState(food.copy(available = it))
                },
            )
        }

        /**
         * Item category
         * User can also add new category
         */
        ExposedDropdownCanAddNewItem(
            options = categories,
            addNewItemPrompt = "Add New Category",
            modifier = defaultModifier,
            listItemToString = { it.categoryName },
            value = categories.find { it == food.category } ?: FoodCategory.noCategory,
            onAddNewItem = {
                viewModel.addAndSetNewCategory(it)
            },
            onSelect = {
                viewModel.updateItemState(food.copy(category = it))
            },
            label = { Text("Category") },
        )

        /**
         * item description
         */
        OutlinedTextField(
            modifier = defaultModifier,
            value = food.description,
            onValueChange = {
                viewModel.updateItemState(food.copy(description = it))
            },
            label = { Text(text = "Description") })

        /**
         * Save button
         */
        Button(
            onClick = {
                viewModel.addItem()
                navigator.navigate(OrderMenuListScreenDestination)
            },
            modifier = defaultModifier
        ) {
            Text(text = "Save")
        }

        /**
         * Delete button
         * Only appears when editing items and not when adding new items
         */
        if (showDelete) {
            OutlinedButton(
                onClick = {
                    viewModel.updateItemState(food.copy(foodDisabled = true))
                    navigator.navigate(OrderMenuListScreenDestination)
                },
                modifier = defaultModifier
            ) {
                Text(text = "Delete")
            }
        }
    }
}

@Composable
fun AddEditFoodScaffold(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            DefaultTopAppBar(
                title,
                canNavigateBack = true,
                navigateUp = {
                    navigator.navigateUp()
                },
            )
        },
        content = content
    )

}

@Preview(showBackground = true)
@Composable
fun AddEditFoodScreenPreview() {
    AairaStationTheme {
        AddEditFoodScaffold("Add") {
            AddEditFood(
                Food(foodName = "Nasi Lemak", priceInCents = 800, description = "Sedap"),
                null,
                listOf(FoodCategory.example)
            )
        }
    }
}