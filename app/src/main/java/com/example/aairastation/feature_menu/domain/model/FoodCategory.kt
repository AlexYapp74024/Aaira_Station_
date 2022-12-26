package com.example.aairastation.feature_menu.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FoodCategory(
    @PrimaryKey(autoGenerate = true)
    val categoryId: Long = 0,
    val categoryName: String = "",
) {
    companion object {
        val noCategory = FoodCategory(0, "No Category")
        val example = FoodCategory(1, "Rice")
    }
}

