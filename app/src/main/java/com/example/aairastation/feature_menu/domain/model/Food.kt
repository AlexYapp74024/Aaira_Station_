package com.example.aairastation.feature_menu.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DecimalFormat

@Entity
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "",
    val categoryID: Long? = null,
    /**
     * Price is counted in cents to prevent inaccuracies when dealing with floating point values
     */
    val priceInCents: Int = 0,
    val available: Boolean = true,
    val description: String = "",
    /**
     * A food is marked disabled if the user has deleted it,
     * we still need to keep it around to record transactions
     */
    val disabled: Boolean = false,
)

/**
 * Since prices are in cents, it has to be converted to RM, as well as appending the unit RM
 */
val Food.formattedPrice: String
    get() = "RM ${DecimalFormat("#,##0.00").format(priceInRinggit)}"

val Food.priceInRinggit: Double
    get() = priceInCents / 100.0


val exampleFood = Food(
    name = "Nasi Lemak",
    priceInCents = 800,
    description = "Nasi lemak is a dish originating in Malay cuisine that consists of fragrant rice cooked in coconut milk and pandan leaf."
)

// Temporary data
val hardCodedList = listOf(
    Food(
        name = "Nasi Lemak",
        priceInCents = 800,
        description = "Nasi lemak is a dish originating in Malay cuisine that consists of fragrant rice cooked in coconut milk and pandan leaf."
    ),
    Food(
        name = "Nasi Goreng",
        priceInCents = 600,
        description = "Nasi goreng is a Southeast Asian fried rice dish, usually cooked with pieces of meat and vegetables. "
    ),
    Food(name = "Kueh Tiau", priceInCents = 700),
    Food(name = "Satay", priceInCents = 200),
    Food(name = "Popia", priceInCents = 100),
    Food(name = "Keropok Lekor", priceInCents = 500),
)