package com.example.aairastation.feature_menu.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.formattedPrice

@Composable
fun EditItemSide(food: Food){
    Column(){
        Row() {
            Text(text = "Items Details")
        }
        Column() {
            Row {
                Text(text = food.name)
            }
            Row() {
                Text(text = food.formattedPrice)
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun Preview() {
    EditItemSide(   Food(name = "Nasi Lemak", priceInCents = 800, description = "Sedap"),)
}