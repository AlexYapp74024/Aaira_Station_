package com.example.aairastation.feature_menu.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aairastation.core.ui_util.BitmapWithDefault
import com.example.aairastation.core.ui_util.TopAppBarCompose
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.formattedPrice
import com.example.aairastation.feature_menu.domain.model.hardCodedList
import com.example.aairastation.feature_menu.presentation.view_models.MenuListViewModel
import com.ramcosta.composedestinations.annotation.Destination

private lateinit var viewModel: MenuListViewModel

//@RootNavGraph(start = true)
@Destination
@Composable
fun MenuListScreen() {
    viewModel = hiltViewModel()

    val items by viewModel.itemsAndCategories.collectAsState(initial = mapOf())
    MenuListContent(hardCodedList)
}

@Composable
fun MenuListContent(
    foodList: List<Food>,
    modifier: Modifier = Modifier
) {
    Scaffold(topBar = { TopAppBarCompose() }) { padding ->

        val categoryList = listOf("Promotion", "Rice", "Noodle", "Drinks")

        Column(modifier = Modifier.padding(padding)) {
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
                        Text(text = food.foodName, modifier = Modifier.weight(1f))
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
                        Text(text = food.foodName, modifier = Modifier.weight(1f))
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
                        Text(text = food.foodName, modifier = Modifier.weight(1f))
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
                        Text(text = food.foodName, modifier = Modifier.weight(1f))
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
    MenuListContent(hardCodedList)
}