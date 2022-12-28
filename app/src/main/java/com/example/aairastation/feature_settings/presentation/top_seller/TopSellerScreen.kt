package com.example.aairastation.feature_settings.presentation.top_seller

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aairastation.core.formatPriceToRM
import com.example.aairastation.core.ui_util.DefaultTopAppBar
import com.example.aairastation.core.ui_util.LazyRowSelector
import com.example.aairastation.feature_settings.domain.model.Grouping
import com.example.aairastation.feature_settings.domain.model.TimeGrouping
import com.example.aairastation.ui.theme.AairaStationTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

private var navigator: DestinationsNavigator = EmptyDestinationsNavigator
private lateinit var viewModel: TopSellerViewModel

@Destination
@Composable
fun TopSellerScreen(navigatorIn: DestinationsNavigator) {
    navigator = navigatorIn
    viewModel = hiltViewModel()

    TopSellerScreen()
}

@Composable
private fun TopSellerScreen() {
    val timeGrouping by viewModel.timeGrouping.collectAsState(initial = TimeGrouping.Daily)
    val grouping by viewModel.grouping.collectAsState(initial = Grouping.Amount)
    val foodList by viewModel.items.collectAsState(initial = listOf())
    val dateRange by viewModel.formattedDateRange.collectAsState(initial = "")

    TopSellerScaffold {
        TopSellerScreenContent(
            timeGrouping = timeGrouping,
            grouping = grouping,
            foodList = foodList,
            dateRange = dateRange,
        )
    }
}

@Composable
private fun TopSellerScreenContent(
    timeGrouping: TimeGrouping,
    grouping: Grouping,
    foodList: List<Pair<String, Int>>,
    dateRange: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        LazyRowSelector(
            items = TimeGrouping.values().toList(),
            value = timeGrouping,
            itemToString = { it.groupName },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            onSelected = {
                viewModel.setTimeGrouping(it)
            }
        )

        Row(
            modifier = Modifier.padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "By:",
                style = MaterialTheme.typography.h5,
            )

            LazyRowSelector(
                items = Grouping.values().toList(),
                value = grouping,
                itemToString = { it.name },
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                onSelected = {
                    viewModel.setGrouping(it)
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        DateRangeSpinner(
            dateRange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Divider(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(32.dp))

        TopSellerItemList(
            foodList = foodList,
            grouping = grouping,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}

@Composable
private fun DateRangeSpinner(
    dateRange: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { viewModel.shiftTimeBackward() }) {
            Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Previous")
        }

        Text(
            text = dateRange,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5
        )

        IconButton(onClick = { viewModel.shiftTimeForward() }) {
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Next")
        }
    }
}

@Composable
private fun TopSellerItemList(
    foodList: List<Pair<String, Int>>,
    grouping: Grouping,
    modifier: Modifier = Modifier,
) {
    @Composable
    fun RowItem(
        item1: String,
        item2: String,
        style: TextStyle = MaterialTheme.typography.h6
    ) {
        Row {
            Text(
                text = item1,
                modifier = Modifier.weight(1f),
                style = style,
            )
            Text(
                text = item2,
                style = style,
            )
        }
    }

    Column(modifier = modifier) {
        val category = when (grouping) {
            Grouping.Price -> "Total (RM)"
            Grouping.Amount -> "Qty"
        }

        RowItem("Item", category, style = MaterialTheme.typography.h5)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            itemsIndexed(foodList) { index, (foodName, total) ->

                val value = when (grouping) {
                    Grouping.Price -> (total / 100.0).formatPriceToRM()
                    Grouping.Amount -> total
                }
                RowItem(
                    "${index + 1}.  $foodName",
                    "$value",
                )
            }
        }
    }
}

@Composable
private fun TopSellerScaffold(
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = "Top Sellers",
                canNavigateBack = true,
                navigateUp = { navigator.navigateUp() })
        },
        modifier = modifier,
        content = content,
    )
}

@Preview
@Composable
private fun Preview() {
    AairaStationTheme {
        TopSellerScaffold {
            TopSellerScreenContent(
                timeGrouping = TimeGrouping.Daily,
                grouping = Grouping.Price,
                foodList = listOf(
                    "Nasi Lemak" to 1000,
                    "Milo" to 400,
                ),
                dateRange = "21/12/20"
            )
        }
    }
}