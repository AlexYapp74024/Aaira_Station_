package com.example.aairastation.core.ui_util

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun <T> TopBarSelector(
    items: List<T>,
    itemToString: (T) -> String,
    lazyListState: LazyListState,
) {
    val coroutineScope = rememberCoroutineScope()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        item { Spacer(modifier = Modifier.width(8.dp)) }

        itemsIndexed(items) { index, item ->
            val isCurrentCategory by remember {
                derivedStateOf { lazyListState.firstVisibleItemIndex == index }
            }

            val textColor by animateColorAsState(
                targetValue = if (isCurrentCategory)
                    MaterialTheme.colors.primary
                else
                    Color.Black
            )

            Text(
                text = itemToString(item),
                style = MaterialTheme.typography.h5,
                color = textColor,
                modifier = Modifier.clickable {
                    coroutineScope.launch {
                        lazyListState.animateScrollToItem(index)
                    }
                }
            )
        }

        item { Spacer(modifier = Modifier.width(8.dp)) }
    }
}