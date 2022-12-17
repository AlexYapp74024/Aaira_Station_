package com.example.aairastation.feature_menu.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
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
            modifier = Modifier.height(64.dp)
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
                Text(text = "Name") }
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
                Text(text = "Name") }
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
                Text(text = "Name") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    EditItemSide(Food(name = "Nasi Lemak", priceInCents = 800, description = "Sedap"))
}