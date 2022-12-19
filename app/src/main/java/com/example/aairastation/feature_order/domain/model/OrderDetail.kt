package com.example.aairastation.feature_order.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OrderDetail(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val orderID: Long,
    val foodID: Long,
    val completed: Boolean = false,
    val completedAt: Long? = null,
)
