package com.example.aairastation.feature_order.domain.model

import androidx.room.Entity

@Entity
data class OrderDetail(
    val id: Int,
    val orderID: Int,
    val foodID: Int,
    val completed: Boolean,
    val completedAt: Long
)
