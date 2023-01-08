package com.example.aairastation.feature_order.domain.use_case.food_order

import com.example.aairastation.feature_order.domain.model.OrderDetail

class IsOrderCompleted {
    operator fun invoke(details: List<OrderDetail>): Boolean {
        /**
         * An order is current when at least 1 entry is not completed.
         * That is, is at least one detail is not completed
         */
        return details.fold(true) { acc, detail ->
            detail.completed && acc
        }
    }
}