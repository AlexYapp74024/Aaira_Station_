package com.example.aairastation.feature_order.domain.use_case

import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_order.domain.use_case.food_order.GetAllOrder
import com.example.aairastation.feature_order.domain.use_case.food_order.GetOrder
import com.example.aairastation.feature_order.domain.use_case.food_order.InsertOrder
import com.example.aairastation.feature_order.domain.use_case.food_order.IsOrderCompleted
import com.example.aairastation.feature_order.domain.use_case.order_detail.GetAllDetail
import com.example.aairastation.feature_order.domain.use_case.order_detail.InsertDetail
import com.example.aairastation.feature_order.domain.use_case.table.DeleteTable
import com.example.aairastation.feature_order.domain.use_case.table.GetAllTable
import com.example.aairastation.feature_order.domain.use_case.table.GetTable
import com.example.aairastation.feature_order.domain.use_case.table.InsertTable

data class OrderUseCases(
    val getAllOrder: GetAllOrder,
    val getOrder: GetOrder,
    val insertOrder: InsertOrder,

    val getAllDetail: GetAllDetail,
    val insertDetail: InsertDetail,

    val deleteTable: DeleteTable,
    val insertTable: InsertTable,
    val getTable: GetTable,
    val getAllTable: GetAllTable,

    val isOrderCompleted: IsOrderCompleted,
) {
    companion object {
        fun create(repository: MainRepository) =
            OrderUseCases(
                getAllOrder = GetAllOrder(repository),
                getOrder = GetOrder(repository),
                insertOrder = InsertOrder(repository),

                getAllDetail = GetAllDetail(repository),
                insertDetail = InsertDetail(repository),

                insertTable = InsertTable(repository),
                deleteTable = DeleteTable(repository),
                getTable = GetTable(repository),
                getAllTable = GetAllTable(repository),

                isOrderCompleted = IsOrderCompleted(),
            )
    }
}