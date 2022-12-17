package com.example.aairastation.feature_order.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OrderDetail(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val orderID: Int,
    val foodID: Int,
    val completed: Boolean = false,
    val completedAt: Long? = null,
)
