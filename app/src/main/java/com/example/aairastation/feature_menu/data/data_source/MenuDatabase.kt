package com.example.aairastation.feature_menu.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.aairastation.feature_menu.domain.model.Food


@Database(
    entities = [Food::class],
    version = 1,
)
abstract class MenuDatabase : RoomDatabase() {

    abstract val dao: MenuDao

    companion object {
        const val DATABASE_NAME = "ForageDb"
    }
}