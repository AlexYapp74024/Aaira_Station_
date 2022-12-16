package com.example.aairastation.feature_menu.domain.use_case

data class MenuUseCase(
    val getAllFood: GetAllFoods,
    val getFood: GetFood,
    val addFood: AddFood
)