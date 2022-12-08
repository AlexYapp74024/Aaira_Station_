package com.example.aairastation.feature_menu.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val category: String = "",
    val price: Double = 0.0,
    val available: Boolean = true,
    val description: String = "",
) : Parcelable


// Temporary data
val hardCodedList = listOf(
    Food(name = "Nasi Lemak", price = 8.00),
    Food(name = "Nasi Goreng", price = 6.00),
    Food(name = "Kueh Tiau", price = 7.00),
    Food(name = "Satay", price = 2.00),
    Food(name = "Popia", price = 1.00),
    Food(name = "Keropok Lekor", price = 5.00),
)