package com.example.aairastation.feature_menu.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val category: String = "",
    val priceInCents: Int = 0,
    val available: Boolean = true,
    val description: String = "",
) : Parcelable

val Food.formattedPrice: String
    get() = "RM ${priceInCents / 100.0}"

// Temporary data
val hardCodedList = listOf(
    Food(name = "Nasi Lemak", priceInCents = 800, description = "Sedap"),
    Food(name = "Nasi Goreng", priceInCents = 600),
    Food(name = "Kueh Tiau", priceInCents = 700),
    Food(name = "Satay", priceInCents = 200),
    Food(name = "Popia", priceInCents = 100),
    Food(name = "Keropok Lekor", priceInCents = 500),
)