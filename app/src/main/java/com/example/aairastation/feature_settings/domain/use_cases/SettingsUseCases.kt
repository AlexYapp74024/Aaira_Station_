package com.example.aairastation.feature_settings.domain.use_cases

import com.example.aairastation.domain.MainRepository

data class SettingsUseCases(
    val loadPreloadedData: LoadPreloadedData,
    val loadFullPreloadedData: LoadFullPreloadedData,
    val getAndParseSales: GetAndParseSales,
) {
    companion object {
        fun create(repository: MainRepository) =
            SettingsUseCases(
                loadPreloadedData = LoadPreloadedData(repository),
                getAndParseSales = GetAndParseSales(repository),
                loadFullPreloadedData = LoadFullPreloadedData(repository),
            )
    }
}