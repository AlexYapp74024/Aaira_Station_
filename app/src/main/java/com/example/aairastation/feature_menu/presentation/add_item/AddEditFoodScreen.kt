package com.example.aairastation.feature_menu.presentation.add_item

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aairastation.core.ui_util.BitmapWithDefault
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodWithImage
import com.example.aairastation.feature_menu.domain.model.formattedPrice
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

private lateinit var viewModel: AddEditViewModel
private var navigator: DestinationsNavigator = EmptyDestinationsNavigator

@Destination
@Composable
fun AddFoodScreen(
    navigatorIn: DestinationsNavigator,
) {
    viewModel = hiltViewModel()
    navigator = navigatorIn

    AddEditFoodScreenContent()
}

@Composable
fun AddEditFoodScreenContent() {
    val food by viewModel.item.collectAsState(initial = FoodWithImage(Food()))
    AddEditFood(
        food = food.item,
        bitmap = food.bitmap,
    )
}

@Composable
fun AddEditFood(
    food: Food,
    bitmap: Bitmap?,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(text = "Items Details")
        BitmapWithDefault(
            bitmap = null,
            contentDescription = null,
            modifier = Modifier.height(310.dp)
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = food.name,
            onValueChange = {

            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            maxLines = 1,
            label = {
                Text(text = "Name")
            }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = food.formattedPrice,
            onValueChange = {

            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            maxLines = 1,
            label = {
                Text(text = "Price")
            }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = food.description,
            onValueChange = {

            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            maxLines = 1,
            label = {
                Text(text = "Description")
            }
        )
        Row(modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 15.dp)) {
            Text(text = "Available")
            Switch(
                checked = true,
                onCheckedChange = null,
                modifier = Modifier.padding(start = 265.dp)
            )
        }

        Button(onClick = { /*null*/ }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Save Changes")
        }

        Button(onClick = { /*null*/ }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Delete Item")

        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    AddEditFood(Food(name = "Nasi Lemak", priceInCents = 800, description = "Sedap"), null)
}