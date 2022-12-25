package com.example.aairastation.feature_menu.presentation.add_edit

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aairastation.core.ui_util.BitmapWithDefault
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodWithImage
import com.example.aairastation.feature_menu.domain.model.priceInRinggit
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

@Destination
@Composable
fun EditFoodScreen(
    foodID: Long,
    navigatorIn: DestinationsNavigator,
) {
    viewModel = hiltViewModel()
    navigator = navigatorIn

    viewModel.retrieveItem(foodID)

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
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier) {
        Text(text = "Items Details")
        BitmapWithDefault(
            bitmap = bitmap,
            contentDescription = null,
            modifier = Modifier.height(310.dp)
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = food.foodName,
            onValueChange = {
                viewModel.updateItemState(food.copy(foodName = it))
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            maxLines = 1,
            label = {
                Text(text = "Name")
            }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = food.priceInRinggit.toString(),
            onValueChange = {
                it.toDoubleOrNull()?.let { price ->
                    val priceInCents = (price * 100).toInt()
                    viewModel.updateItemState(food.copy(priceInCents = priceInCents))
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            maxLines = 1,
            label = {
                Text(text = "Price")
            }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = food.description,
            onValueChange = {
                viewModel.updateItemState(food.copy(description = it))
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

        Button(onClick = { viewModel.addItem() }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Save Changes")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    AddEditFood(Food(foodName = "Nasi Lemak", priceInCents = 800, description = "Sedap"), null)
}