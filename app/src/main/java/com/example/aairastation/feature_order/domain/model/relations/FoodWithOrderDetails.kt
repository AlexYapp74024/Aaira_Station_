package com.example.aairastation.feature_order.domain.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_order.domain.model.OrderDetail

data class FoodWithOrderDetails(
    @Embedded
    val food: Food,
    @Relation(
        parentColumn = "id",
        entityColumn = "foodID"
    )
    val orderDetailList: List<OrderDetail>,
)
