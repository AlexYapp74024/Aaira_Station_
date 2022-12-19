package com.example.aairastation.feature_menu.presentation.add_item

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aairastation.core.ui_util.BitmapWithDefault
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.formattedPrice

@Composable
fun EditItemSide(food: Food) {
    Column() {
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
//                    if (!it.contains('\n')) viewModel.updateItemState(item.copy(name = it))
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
//                    if (!it.contains('\n')) viewModel.updateItemState(item.copy(name = it))
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
//                    if (!it.contains('\n')) viewModel.updateItemState(item.copy(name = it))
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
    EditItemSide(Food(name = "Nasi Lemak", priceInCents = 800, description = "Sedap"))
}