package com.example.aairastation.feature_menu.presentation.menu_list

import android.graphics.Bitmap
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aairastation.core.ui_util.BitmapWithDefault
import com.example.aairastation.core.ui_util.DefaultTopAppBar
import com.example.aairastation.core.ui_util.LazyRowIndexedSelector
import com.example.aairastation.destinations.AddFoodScreenDestination
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.example.aairastation.feature_menu.domain.model.formattedPrice
import com.example.aairastation.ui.theme.AairaStationTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

private lateinit var viewModel: EditMenuListViewModel
private var navigator: DestinationsNavigator = EmptyDestinationsNavigator

@Destination
@Composable
fun EditListScreen(navigatorIn: DestinationsNavigator) {
    viewModel = hiltViewModel()
    navigator = navigatorIn

    EditMenuList()
}

@Composable
private fun EditMenuList() {
    val items by viewModel.itemsAndCategories.collectAsState(initial = mapOf())

    EditMenuListScaffold { padding ->
        EditMenuListContent(items, paddingValues = padding)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun EditMenuListContent(
    foodList: Map<FoodCategory, Map<Food, Flow<Bitmap?>>>,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
) {
    Column(modifier = modifier.fillMaxSize()) {
        val columnState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        /**
         * Displays the list of categories
         * When selected, will auto scrolled to the first item of said category
         */
        LazyRowIndexedSelector(
            items = foodList.keys.toList(),
            isCurrentValue = { index, _ ->
                columnState.firstVisibleItemIndex == index
            },
            itemToString = { it.categoryName },
            onSelected = { index, _ ->
                /**
                 * Scrolls to the selected category
                 */
                coroutineScope.launch {
                    columnState.animateScrollToItem(index)
                }
            }
        )

        /**
         * Displays the menu
         */
        LazyColumn(
            state = columnState,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            foodList.onEach { (category, items) ->
                stickyHeader {
                    Text(
                        text = category.categoryName,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier
                            .background(color = MaterialTheme.colors.primaryVariant)
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                    )
                }

                item {
                    FoodList(
                        items,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding()))
    }
}


@Composable
private fun FoodList(
    foodList: Map<Food, Flow<Bitmap?>>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        foodList.onEach { (food, bitmapFlow) ->
            FoodListItemEntry(
                food = food,
                bitmapFlow = bitmapFlow,
                itemOnClick = {
                    viewModel.viewItem(
                        navigator, food.foodId
                    )
                },
                modifier = Modifier.background(color = MaterialTheme.colors.background),
            )
        }
    }
}

@Composable
private fun FoodListItemEntry(
    food: Food,
    bitmapFlow: Flow<Bitmap?>,
    itemOnClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .height(72.dp)
        .padding(horizontal = 8.dp)
        .clickable {
            itemOnClick()
        }) {

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = food.foodName, style = MaterialTheme.typography.h6)
            if (food.description.isNotEmpty())
                Text(text = food.description, style = MaterialTheme.typography.subtitle1)
            Text(text = food.formattedPrice)
        }

        Spacer(modifier = Modifier.width(8.dp))

        val bitmap by bitmapFlow.collectAsState(initial = null)
        bitmap?.let {
            BitmapWithDefault(
                bitmap = bitmap,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .padding(4.dp),
                contentScaleIfNotNull = ContentScale.Fit,
            )
        }
    }
}


@Composable
private fun EditMenuListScaffold(
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = "Menu",
                canNavigateBack = true,
                navigateUp = { navigator.navigateUp() })
        },
        floatingActionButton = {
            AddItemFloatingActionButton(floatingActionBtnOnClick = {
                navigator.navigate(AddFoodScreenDestination)
            })
        },
        modifier = modifier,
        content = content,
    )
}


@Composable
private fun AddItemFloatingActionButton(
    floatingActionBtnOnClick: () -> Unit
) {
    FloatingActionButton(
        onClick = floatingActionBtnOnClick,
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary
    ) {
        Icon(
            Icons.Filled.Add, contentDescription = "Add Item"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    val foodMap = mapOf(
        Food.example.copy(foodId = 1) to flowOf(null),
        Food.example.copy(foodId = 2) to flowOf(null),
        Food.example.copy(foodId = 3) to flowOf(null),
        Food.example.copy(foodId = 4) to flowOf(null)
    )

    AairaStationTheme {
        EditMenuListScaffold {
            EditMenuListContent(
                mapOf(
                    FoodCategory.example.copy(categoryId = 1) to foodMap,
                    FoodCategory.example.copy(categoryId = 2) to foodMap,
                    FoodCategory.example.copy(categoryId = 3) to foodMap,
                ),
            )
        }
    }
}