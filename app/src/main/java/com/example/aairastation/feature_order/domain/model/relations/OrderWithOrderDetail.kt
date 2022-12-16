package com.example.aairastation.feature_order.domain.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.OrderDetail

data class OrderWithOrderDetail(
    @Embedded
    val foodOrder: FoodOrder,
    @Relation(
        parentColumn = "id",
        entityColumn = "orderID"
    )
    val orderDetailList: List<OrderDetail>
)
