package com.example.aairastation.feature_menu.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.aairastation.core.formatPriceToRM
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Food(
    @PrimaryKey(autoGenerate = true)
    val foodId: Long = 0,
    val foodName: String = "",
    @Embedded
    val category: FoodCategory = FoodCategory.noCategory,
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
    val foodDisabled: Boolean = false,
) {
    companion object {
        val example = Food(
            foodName = "Nasi Lemak",
            priceInCents = 800,
            description = "Nasi lemak is a dish"
        )
    }
}

/**
 * Since prices are in cents, it has to be converted to RM, as well as appending the unit RM
 */
val Food.formattedPrice: String
    get() = priceInRinggit.formatPriceToRM()

val Food.priceInRinggit: Double
    get() = priceInCents / 100.0