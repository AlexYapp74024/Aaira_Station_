package com.example.aairastation.feature_menu.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class FoodCategory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val name: String = "",
) : Parcelable

val exampleFoodCategory = FoodCategory(1, "Rice")

