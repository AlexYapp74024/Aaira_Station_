package com.example.aairastation.feature_order.domain.model

import androidx.room.Entity

@Entity
data class Order(
    val id: Int,
    val tableID: Int,
    val createdAt: Long,
)
