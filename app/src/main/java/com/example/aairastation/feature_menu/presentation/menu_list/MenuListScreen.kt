package com.example.aairastation.feature_menu.presentation.menu_list

import android.graphics.Bitmap
import android.view.MenuItem
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.aairastation.core.ui_util.BitmapWithDefault
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Locale.Category

private lateinit var viewModel: MenuListViewModel

@Entity
data class MenuItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = " ",
    val price: String = " ",
    val description: String = " ",
)

data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = " ",
)

@RootNavGraph(start = true)
@Destination
@Composable
fun MenuListScreen() {
    viewModel = hiltViewModel()

    val items by viewModel.itemsAndCategories.collectAsState(initial = mapOf())
    MenuItemListScaffold(Modifier)
}

@Preview(showBackground = true)
@Composable
fun MenuItemListCategoryListPreview() {

    val menuItemList = mapOf(
        MenuItem(
            id = 1,
            name = "Nasi Lemak",
            price = "RM 5.00",
            description = "This is very delicious"
        ) to flow { emit(null)  },
        MenuItem(
            id = 1,
            name = "Nasi Goreng",
            price = "RM 7.00",
            description = "This is very suspicious"
        ) to flow { emit(null)  },
    )

    val items = mapOf(
        Category(1,"Nasi") to menuItemList,
        Category(2,"Western") to menuItemList,
        Category(3,"Mee") to menuItemList,
    )

    MenuItemListScaffold(Modifier)
}

@Composable
fun MenuItemListCategoryList(
    menuItemList: Map<Category, Map<MenuItem, Flow<Bitmap?>>>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(menuItemList.toList()) { (category,items) ->
            Text(text = category.name)
            MenuItemList(items)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MenuItemListPreview() {
    val menuItemList = mapOf(
            MenuItem(
                id = 1,
                name = "Nasi Lemak",
                price = "RM 5.00",
                description = "This is very delicious"
            ) to flow { emit(null)  },
            MenuItem(
                id = 1,
                name = "Nasi Goreng",
                price = "RM 7.00",
                description = "This is very suspicious"
            ) to flow { emit(null)  }
        )

    MenuItemListScaffold(modifier = Modifier)
}

@Composable
fun MenuItemList(
    menuItemList: Map<MenuItem, Flow<Bitmap?>>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        menuItemList.onEach { (item,bitmapFlow) ->
            MenuListItemEntry(
                item = item,
                bitmapFlow = bitmapFlow,
            )
        }
    }
}

@Composable
fun MenuListItemEntry(
    item: MenuItem,
    bitmapFlow: Flow<Bitmap?>,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier
        .fillMaxSize()
        .height(72.dp)

    ) {
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
            Text(text = item.id, style = MaterialTheme.typography.h5)
            Text(text = item.description, style = MaterialTheme.typography.body1)

        }
    }
}

@Composable
fun MenuItemListScaffold(
    modifier: Modifier.Companion,
) {
    Scaffold(
        modifier = Modifier,
        topBar = {
            ManuTopAppBar (
                Title = "Menu",
                canNavigateBack = false,
                navigateUp = {},
            )
        },

        content = content
        )
}

@Composable
fun AddItemFloatingActionButton(
    floatingActionBtnOnClick: () -> Unit
) {
    FloatingActionButton(
        onClick = floatingActionBtnOnClick,
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.surface
    ) {
        Icon(
            Icons.Filled.Add, contentDescription = "Add Item"
        )
    }
}

//@Composable
//fun MenuListContent(
//    foodList: List<Food>,
//    modifier: Modifier = Modifier
//) {
//    Scaffold(topBar = { TopAppBarCompose() }) { padding ->
//
//        val categoryList = listOf("Promotion", "Rice", "Noodle", "Drinks")
//
//        Column(modifier = Modifier.padding(padding)) {
//            LazyRow(
//                modifier = Modifier
//                    .padding(vertical = 16.dp)
//                    .padding(horizontal = 16.dp)
//                    .fillMaxWidth()
//            ) {
//                items(categoryList) { category ->
//                    Text(
//                        text = category,
//                        modifier = Modifier.padding(horizontal = 8.dp),
//                        fontSize = 20.sp,
//                        textAlign = TextAlign.Center,
//                    )
//                }
//            }
//
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp)
//            ) {
//
//                item {
//                    Text(
//                        text = "Promotion",
//                        fontSize = 20.sp,
//                        modifier = Modifier.padding(top = 20.dp)
//                    )
//                }
//                items(foodList) { food ->
//
//                    Row {
//                        Text(text = food.name, modifier = Modifier.weight(1f))
//                        Text(text = food.priceInCents.toString())
////                        Text(text = food.description.toString())
//                    }
//                }
//
//                item {
//                    Text(text = "Rice", fontSize = 20.sp, modifier = Modifier.padding(top = 20.dp))
//                }
//                items(foodList) { food ->
//
//                    Row {
//                        BitmapWithDefault(
//                            null, null,
////                            modifier = Modifier.fillMaxWidth()
//                        )
//                        Text(text = food.name, modifier = Modifier.weight(1f))
//                        Text(text = food.formattedPrice)
////                        Text(text = food.description)
////                        Text(text = food.description.toString())
//                    }
//                }
//
//                item {
//                    Text(
//                        text = "Noodles", fontSize = 20.sp, modifier = Modifier.padding(top = 20.dp)
//                    )
//                }
//                items(foodList) { food ->
//
//                    Row {
//                        Text(text = food.name, modifier = Modifier.weight(1f))
//                        Text(text = food.priceInCents.toString())
//                    }
//                }
//
//
//                item {
//                    Text(
//                        text = "Drinks", fontSize = 20.sp, modifier = Modifier.padding(top = 20.dp)
//                    )
//                }
//                items(foodList) { food ->
//
//                    Row {
//                        Text(text = food.name, modifier = Modifier.weight(1f))
//                        Text(text = food.priceInCents.toString())
////                        Text(text = food.description.toString())
//                    }
//                }
//            }
//        }
//    }
//}

@Composable
fun ManuTopAppBar(
    Title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.material.TopAppBar(
        title = { Text(Title) },
        modifier = modifier,
        navigationIcon = if (canNavigateBack) {
            {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack, contentDescription = null
                    )
                }
            }
        } else null
    )
}

