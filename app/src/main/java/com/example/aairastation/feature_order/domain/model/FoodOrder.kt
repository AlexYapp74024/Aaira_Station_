package com.example.aairastation.feature_order.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FoodOrder(
    @PrimaryKey(autoGenerate = true)
    val orderId: Long,
    /**
     * a Null tableID is for takeaways since table number are not applicable in those scenarios
     */
    @Embedded val table: NumberedTable?,
    val createdAt: Long? = System.currentTimeMillis(),
) {
    companion object {
        val example = FoodOrder(1, NumberedTable.example)
    }
}
