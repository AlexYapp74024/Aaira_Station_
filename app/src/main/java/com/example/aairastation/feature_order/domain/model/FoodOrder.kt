package com.example.aairastation.feature_order.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FoodOrder(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    /**
     * a Null tableID is for takeaways since table number are not applicable in those scenarios
     */
    val tableID: Int?,
    val createdAt: Long? = System.currentTimeMillis(),
)
