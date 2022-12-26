package com.example.aairastation.feature_order.presentation.order_menu

import android.graphics.Bitmap
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aairastation.core.ui_util.BitmapWithDefault
import com.example.aairastation.core.ui_util.BottomNavItems
import com.example.aairastation.core.ui_util.DefaultBottomNavigation
import com.example.aairastation.core.ui_util.DefaultTopAppBar
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
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

private lateinit var viewModel: OrderMenuListViewModel
private var navigator: DestinationsNavigator = EmptyDestinationsNavigator

@Destination
@Composable
fun OrderMenuListScreen(navigatorIn: DestinationsNavigator) {
    viewModel = hiltViewModel()
    navigator = navigatorIn

    OrderMenuListScreen()
}

@Composable
private fun OrderMenuListScreen() {
    val items by viewModel.itemsAndCategories.collectAsState(initial = mapOf())
    val foodQuantity by viewModel.foodQuantity.collectAsState(initial = mapOf())

    OrderMenuListScaffold {
        OrderMenuListContent(items, foodQuantity)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OrderMenuListContent(
    foodList: Map<FoodCategory, Map<Food, Flow<Bitmap?>>>,
    foodQuantity: Map<Food, Int>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        val columnState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            item { Spacer(modifier = Modifier.width(8.dp)) }

            itemsIndexed(foodList.keys.toList()) { index, category ->
                val isCurrentCategory by remember {
                    derivedStateOf { columnState.firstVisibleItemIndex == index }
                }

                val textColor by animateColorAsState(
                    targetValue = if (isCurrentCategory)
                        MaterialTheme.colors.primary
                    else
                        Color.Black
                )

                Text(
                    text = category.categoryName,
                    style = MaterialTheme.typography.h5,
                    color = textColor,
                    modifier = Modifier.clickable {
                        coroutineScope.launch {
                            columnState.animateScrollToItem(index)
                        }
                    }
                )
            }

            item { Spacer(modifier = Modifier.width(8.dp)) }
        }

        LazyColumn(
            state = columnState,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
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
                        foodQuantity,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                    )
                }
            }
            item { Spacer(modifier = Modifier.height(40.dp)) }
        }

    }
}

@Composable
private fun FoodList(
    foodList: Map<Food, Flow<Bitmap?>>,
    foodQuantity: Map<Food, Int>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        foodList.onEach { (food, bitmapFlow) ->

            val quantity = foodQuantity[food] ?: 0

            val swipeAction = SwipeAction(
                icon = { Icon(imageVector = Icons.Default.Delete, contentDescription = null) },
                background = MaterialTheme.colors.error,
                onSwipe = { viewModel.decrementFood(food) },
            )

            val foodListWrapper: @Composable () -> Unit = {
                val foodListEntry: @Composable () -> Unit = {
                    FoodListItemEntry(
                        food = food,
                        bitmapFlow = bitmapFlow,
                        itemOnClick = { viewModel.incrementFood(food) },
                        modifier = Modifier.background(color = MaterialTheme.colors.background),
                    )
                }

                // Only show badge when quantity > 0
                if (quantity > 0) {
                    BadgedBox(badge = {
                        Badge { Text("$quantity", fontSize = 15.sp) }
                    }) {
                        foodListEntry()
                    }
                } else {
                    foodListEntry()
                }
            }

            // Swipe is only enabled when quantity > 0
            if (quantity > 0) {
                SwipeableActionsBox(
                    endActions = listOf(swipeAction),
                    startActions = listOf(swipeAction),
                    modifier = Modifier.background(color = MaterialTheme.colors.background),
                    swipeThreshold = Dp(0.5f)
                ) {
                    foodListWrapper()
                }
            } else {
                foodListWrapper()
            }
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
            modifier = Modifier.fillMaxWidth()
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
private fun OrderMenuListScaffold(
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
private fun DefaultPreview() {
    val foodMap = mapOf(
        Food.example.copy(foodId = 1) to flowOf(null),
        Food.example.copy(foodId = 2) to flowOf(null),
        Food.example.copy(foodId = 3) to flowOf(null),
        Food.example.copy(foodId = 4) to flowOf(null)
    )

    AairaStationTheme {
        OrderMenuListScaffold {
            OrderMenuListContent(
                mapOf(
                    FoodCategory.example.copy(categoryId = 1) to foodMap,
                    FoodCategory.example.copy(categoryId = 2) to foodMap,
                    FoodCategory.example.copy(categoryId = 3) to foodMap,
                ),
                foodQuantity = mapOf(Food.example.copy(foodId = 1) to 1),
            )
        }
    }
}