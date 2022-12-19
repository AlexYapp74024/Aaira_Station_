package com.example.aairastation.feature_menu.presentation.menu_list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aairastation.core.ui_util.BitmapWithDefault
import com.example.aairastation.core.ui_util.TopAppBarCompose
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.formattedPrice
import com.example.aairastation.feature_menu.domain.model.hardCodedList

@Composable
fun MenuListScreen() {
    MenuMainSide(hardCodedList)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MenuMainSide(
    foodList: List<Food>
) {
    Scaffold(topBar = { TopAppBarCompose() }) {

        val categoryList = listOf("Promotion", "Rice", "Noodle", "Drinks")

        Column {
            LazyRow(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                items(categoryList) { category ->
                    Text(
                        text = category,
                        modifier = Modifier.padding(horizontal = 8.dp),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                item {
                    Text(
                        text = "Promotion",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 20.dp)
                    )
                }
                items(foodList) { food ->

                    Row {
                        Text(text = food.name, modifier = Modifier.weight(1f))
                        Text(text = food.priceInCents.toString())
//                        Text(text = food.description.toString())
                    }
                }

                item {
                    Text(text = "Rice", fontSize = 20.sp, modifier = Modifier.padding(top = 20.dp))
                }
                items(foodList) { food ->

                    Row {
                        BitmapWithDefault(
                            null, null,
//                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(text = food.name, modifier = Modifier.weight(1f))
                        Text(text = food.formattedPrice)
//                        Text(text = food.description)
//                        Text(text = food.description.toString())
                    }
                }

                item {
                    Text(
                        text = "Noodles", fontSize = 20.sp, modifier = Modifier.padding(top = 20.dp)
                    )
                }
                items(foodList) { food ->

                    Row {
                        Text(text = food.name, modifier = Modifier.weight(1f))
                        Text(text = food.priceInCents.toString())
                    }
                }


                item {
                    Text(
                        text = "Drinks", fontSize = 20.sp, modifier = Modifier.padding(top = 20.dp)
                    )
                }
                items(foodList) { food ->

                    Row {
                        Text(text = food.name, modifier = Modifier.weight(1f))
                        Text(text = food.priceInCents.toString())
//                        Text(text = food.description.toString())
                    }
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