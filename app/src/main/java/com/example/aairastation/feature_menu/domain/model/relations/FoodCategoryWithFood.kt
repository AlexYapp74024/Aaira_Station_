package com.example.aairastation.feature_menu.domain.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory

data class FoodCategoryWithFood(
    @Embedded val category: FoodCategory,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryID"
    )
    val foodList: List<Food>
)
