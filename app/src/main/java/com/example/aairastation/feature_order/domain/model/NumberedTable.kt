package com.example.aairastation.feature_order.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NumberedTable(
    @PrimaryKey(autoGenerate = true)
    val tableId: Long = 0,
    val tableNumber: Long,
    /**
     * Marks a table is disabled when the table is no longer in use
     */
    val tableDisabled: Boolean = false
) {
    companion object {
        val example = NumberedTable(0, 0)
    }
}
