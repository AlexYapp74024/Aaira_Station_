package com.example.aairastation.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.aairastation.feature_menu.domain.model.Food
import com.example.aairastation.feature_menu.domain.model.FoodCategory
import com.example.aairastation.feature_order.domain.model.FoodOrder
import com.example.aairastation.feature_order.domain.model.OrderDetail
import com.example.aairastation.feature_order.domain.model.NumberedTable


@Database(
    entities = [Food::class, FoodCategory::class, FoodOrder::class, OrderDetail::class, NumberedTable::class],
    version = 1,
)
abstract class MainDatabase : RoomDatabase() {

    abstract val dao: MainDao

    companion object {
        const val DATABASE_NAME = "AairaStationDb"
    }
}