package com.example.aairastation.core.ui_util

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun <T> LazyRowIndexedSelector(
    items: List<T>,
    isCurrentValue: (Int, T) -> Boolean,
    itemToString: (T) -> String,
    modifier: Modifier = Modifier,
    onSelected: (Int, T) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        item { Spacer(modifier = Modifier.width(8.dp)) }

        itemsIndexed(items) { index, item ->
            val isCurrentItem = isCurrentValue(index, item)

            val textColor by animateColorAsState(
                targetValue = if (isCurrentItem)
                    MaterialTheme.colors.primary
                else
                    Color.Black
            )

            Text(
                text = itemToString(item),
                style = MaterialTheme.typography.h5,
                color = textColor,
                modifier = Modifier.clickable {
                    onSelected(index, item)
                }
            )
        }

        item { Spacer(modifier = Modifier.width(8.dp)) }
    }
}


@Composable
fun <T> LazyRowSelector(
    items: List<T>,
    value: T,
    itemToString: (T) -> String,
    modifier: Modifier = Modifier,
    onSelected: (T) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        item { Spacer(modifier = Modifier.width(8.dp)) }

        items(items) { item ->
            val isCurrentItem = item == value

            val textColor by animateColorAsState(
                targetValue = if (isCurrentItem)
                    MaterialTheme.colors.primary
                else
                    Color.Black
            )

            Text(
                text = itemToString(item),
                style = MaterialTheme.typography.h5,
                color = textColor,
                modifier = Modifier.clickable {
                    onSelected(item)
                }
            )
        }

        item { Spacer(modifier = Modifier.width(8.dp)) }
    }
}