package com.example.aairastation.feature_menu.presentation.menu_list

import android.graphics.Bitmap
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aairastation.core.ui_util.BitmapWithDefault
import com.example.aairastation.core.ui_util.BottomNavItems
import com.example.aairastation.core.ui_util.DefaultBottomNavigation
import com.example.aairastation.core.ui_util.DefaultTopAppBar
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

private lateinit var viewModel: MenuListViewModel
private var navigator: DestinationsNavigator = EmptyDestinationsNavigator

@Destination
@Composable
fun MenuListScreen(navigatorIn: DestinationsNavigator) {
    viewModel = hiltViewModel()
    navigator = navigatorIn

    MenuListScreen()
}

@Composable
fun MenuListScreen() {
    val items by viewModel.itemsAndCategories.collectAsState(initial = mapOf())

    MenuListScaffold {
        MenuListContent(items, itemOnClick = {

        })
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MenuListContent(
    foodList: Map<FoodCategory, Map<Food, Flow<Bitmap?>>>,
    itemOnClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        val columnState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(foodList.keys.toList()) { index, category ->
                Text(
                    text = category.categoryName,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.clickable {
                        coroutineScope.launch {
                            columnState.animateScrollToItem(index)
                        }
                    }
                )
            }
        }

        LazyColumn(
            state = columnState,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            foodList.onEach { (category, items) ->
                stickyHeader {
                    Text(
                        text = category.categoryName,
                        style = MaterialTheme.typography.h5
                    )
                }

                item {
                    FoodList(items, itemOnClick)
                }
            }
        }
    }
}


@Composable
fun FoodList(
    foodList: Map<Food, Flow<Bitmap?>>,
    itemOnClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        foodList.onEach { (food, bitmapFlow) ->
            FoodListItemEntry(
                food = food,
                bitmapFlow = bitmapFlow,
                itemOnClick = itemOnClick
            )
        }
    }
}

@Composable
fun FoodListItemEntry(
    food: Food,
    bitmapFlow: Flow<Bitmap?>,
    itemOnClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .height(72.dp)
        .clickable {
            itemOnClick()
        }) {

        val bitmap by bitmapFlow.collectAsState(initial = null)
        BitmapWithDefault(
            bitmap = bitmap,
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .padding(4.dp),
            contentScaleIfNotNull = ContentScale.Fit,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = food.foodName, style = MaterialTheme.typography.h5)
        }
    }
}


@Composable
fun MenuListScaffold(
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(title = "Menu", canNavigateBack = false, navigateUp = {})
        },
        bottomBar = {
            DefaultBottomNavigation(
                currentItem = BottomNavItems.Menu,
                navigator = navigator
            )
        },
        modifier = modifier,
        content = content,
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    MenuListContent(
        mapOf(
            FoodCategory.example to mapOf(
                Food.example to flowOf(null),
                Food.example to flowOf(null),
                Food.example to flowOf(null),
                Food.example to flowOf(null)
            ),
            FoodCategory.example to mapOf(
                Food.example to flowOf(null),
                Food.example to flowOf(null),
                Food.example to flowOf(null),
                Food.example to flowOf(null)
            ),
            FoodCategory.example to mapOf(
                Food.example to flowOf(null),
                Food.example to flowOf(null),
                Food.example to flowOf(null),
                Food.example to flowOf(null)
            ),
        ),

        itemOnClick = {}
    )
}