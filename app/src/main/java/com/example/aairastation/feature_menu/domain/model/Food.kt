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

val Food.formattedDescription: String
    get() = "$description"

// Temporary data
val hardCodedList = listOf(
    Food(name = "Nasi Lemak", priceInCents = 800, description = "Nasi lemak is a dish originating in Malay cuisine that consists of fragrant rice cooked in coconut milk and pandan leaf."),
    Food(name = "Nasi Goreng", priceInCents = 600, description = "Nasi goreng is a Southeast Asian fried rice dish, usually cooked with pieces of meat and vegetables. "),
    Food(name = "Kueh Tiau", priceInCents = 700),
    Food(name = "Satay", priceInCents = 200),
    Food(name = "Popia", priceInCents = 100),
    Food(name = "Keropok Lekor", priceInCents = 500),
)