package com.example.aairastation.feature_menu.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FoodCategory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val name: String = "",
)

val exampleFoodCategory = FoodCategory(1, "Rice")

