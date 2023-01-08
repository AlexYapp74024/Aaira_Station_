package com.example.aairastation.feature_settings.presentation.sales_report

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aairastation.core.formatTo2dp
import com.example.aairastation.core.ui_util.DefaultTopAppBar
import com.example.aairastation.core.ui_util.LazyRowSelector
import com.example.aairastation.feature_settings.domain.model.TimeGrouping
import com.example.aairastation.ui.theme.AairaStationTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

private lateinit var viewModel: SalesReportViewModel
private var navigator: DestinationsNavigator = EmptyDestinationsNavigator

@Destination
@Composable
fun SalesReportScreen(navigatorIn: DestinationsNavigator) {
    navigator = navigatorIn
    viewModel = hiltViewModel()

    SalesReportScreen()
}

@Composable
private fun SalesReportScreen() {
    val timeGrouping by viewModel.timeGrouping.collectAsState(initial = TimeGrouping.Daily)
    val entries by viewModel.entries.collectAsState(initial = listOf())

    SalesReportScaffold {
        SalesReportScreenContent(
            timeGrouping = timeGrouping,
            entries = entries,
        )
    }
}


@Composable
private fun SalesReportScreenContent(
    timeGrouping: TimeGrouping,
    entries: List<SalesReportViewModel.Entry>,
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

        Spacer(modifier = Modifier.height(16.dp))

        Divider(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(32.dp))

        SalesReportItemList(
            entries = entries,
            modifier = Modifier.padding(start = 16.dp, end = 32.dp)
        )
    }
}

@Composable
private fun SalesReportItemList(
    entries: List<SalesReportViewModel.Entry>,
    modifier: Modifier = Modifier,
) {
    /**
     * Nested function for displaying each entry
     */
    @Composable
    fun RowItem(
        item1: String,
        item2: String,
        item3: String,
        style: TextStyle = MaterialTheme.typography.h6
    ) {
        Row {
            Text(
                text = item1, style = style,
                modifier = Modifier.fillMaxWidth(.1f)
            )
            Text(
                text = item2, style = style,
                modifier = Modifier.weight(1f),
            )
            Text(text = item3, style = style)
        }
    }

    Column(modifier = modifier) {

        RowItem(
            "",
            "Period",
            "Total (RM)",
            MaterialTheme.typography.h6
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            /**
             * (number, dateRange, total) is a convenient way of breaking down the Entry class into its componends
             * for more info see the [Entry] class in [SalesReportViewModel.kt]
             */
            items(entries) { (number, dateRange, total) ->
                RowItem(
                    "$number.",
                    dateRange,
                    (total / 100.0).formatTo2dp(),
                )
            }
        }
    }
}

@Composable
private fun SalesReportScaffold(
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = "Sales Report",
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
        SalesReportScaffold {
            SalesReportScreenContent(
                timeGrouping = TimeGrouping.Daily,
                entries = listOf(
                    SalesReportViewModel.Entry(1, "Period 1", 100),
                    SalesReportViewModel.Entry(2, "Period 2", 200),
                ),
            )
        }
    }
}