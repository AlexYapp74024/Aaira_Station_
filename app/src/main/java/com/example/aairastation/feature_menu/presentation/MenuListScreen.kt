package com.example.aairastation.feature_menu.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.formattedPrice
import com.example.aairastation.feature_menu.domain.model.hardCodedList

@Composable
fun MenuListScreen() {
    MenuMainSide(hardCodedList)
}

@Composable
fun MenuMainSide(
    foodList: List<Food>
) {
    Column {
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Name", modifier = Modifier.weight(1f))
            Text(text = "Price")
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(foodList) { food ->
                Row {
                    Text(text = food.name, modifier = Modifier.weight(1f))
                    Text(text = food.formattedPrice)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MenuMainSide(hardCodedList)
}