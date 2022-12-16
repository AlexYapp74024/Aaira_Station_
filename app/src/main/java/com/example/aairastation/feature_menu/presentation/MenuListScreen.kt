package com.example.aairastation.feature_menu.presentation

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.hardCodedList
import com.google.android.material.tabs.TabLayout

@Composable
fun MenuListScreen() {
    MenuMainSide(hardCodedList)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MenuMainSide(
    foodList: List<Food>
) {
    Scaffold(
        topBar = { TopAppBarCompose() }
    ) {

        Column {
            Row(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Promotion", modifier = Modifier.weight(1f), fontSize = 20.sp,textAlign = TextAlign.Center,)
                Text(text = "Rice", modifier = Modifier.weight(1f), fontSize = 20.sp,textAlign = TextAlign.Center)
                Text(text = "Noodle", modifier = Modifier.weight(1f), fontSize = 20.sp, textAlign = TextAlign.Center)
                Text(text = "Drinks", fontSize = 20.sp,textAlign = TextAlign.Center)
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                item {
                    Text(text = "Promotion", fontSize = 20.sp, modifier = Modifier.padding(top = 20.dp))
                }
                items(foodList) { food ->

                    Row {
                        Text(text = food.name, modifier = Modifier.weight(1f))
                        Text(text = food.priceInCents.toString())
//                        Text(text = food.description.toString())
                    }
                }

                item {
                    Text(text = "Rice", fontSize = 20.sp,modifier = Modifier.padding(top = 20.dp))
                }
                items(foodList) { food ->

                    Row {
                        Text(text = food.name, modifier = Modifier.weight(1f))
                        Text(text = food.priceInCents.toString())
//                        Text(text = food.description.toString())
                    }
                }

                item {
                    Text(text = "Noodles", fontSize = 20.sp,modifier = Modifier.padding(top = 20.dp))
                }
                items(foodList) { food ->

                    Row {
                        Text(text = food.name, modifier = Modifier.weight(1f))
                        Text(text = food.priceInCents.toString())
//                        Text(text = food.description.toString())
                    }
                }

                item {
                    Text(text = "Drinks", fontSize = 20.sp,modifier = Modifier.padding(top = 20.dp))
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



@Composable
fun TopAppBarCompose(){

    val context = LocalContext.current

    TopAppBar(
        
        title = { Text(
            text = "Order",
            fontSize = 30.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
            )
                },

        navigationIcon = {
            IconButton(onClick = {
                Toast.makeText(context,"Menu",Toast.LENGTH_SHORT).show()
            }) {
                Icon(Icons.Default.ArrowBack,"Menu")
            }
                         },
        actions = {
            IconButton(onClick = {
                Toast.makeText(context,"Search",Toast.LENGTH_SHORT).show()
            }) {
                Icon(Icons.Default.Search,"Menu")
            }

            IconButton(onClick = {
                Toast.makeText(context,"Home",Toast.LENGTH_SHORT).show()
            }) {
                Icon(Icons.Default.Home,"Menu")
            }
        },
        backgroundColor = Color.LightGray,
        contentColor = Color.Black
    )

}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    MenuMainSide(hardCodedList)
}