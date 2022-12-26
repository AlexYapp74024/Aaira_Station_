package com.example.aairastation.feature_order.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.priceInRinggit
import java.text.DecimalFormat

@Entity
data class OrderDetail(
    @PrimaryKey(autoGenerate = true)
    val detailId: Long = 0,
    @Embedded val order: FoodOrder,
    @Embedded val food: Food,
    val amount: Int,
    val completed: Boolean = false,
    val completedAt: Long? = null,
)

fun Food.formattedPriceAmount(amount: Int): String =
    "RM ${DecimalFormat("#,##0.00").format(priceInRinggit * amount)}"
