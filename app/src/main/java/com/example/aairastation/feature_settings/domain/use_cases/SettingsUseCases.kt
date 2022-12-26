package com.example.aairastation.feature_settings.domain.use_cases

import com.example.aairastation.domain.MainRepository

data class SettingsUseCases(
    val salesReport: SalesReport,
    val loadPreloadedData: LoadPreloadedData,
    val topSeller: TopSeller,
) {
    companion object {
        fun create(repository: MainRepository) =
            SettingsUseCases(
                salesReport = SalesReport(repository),
                loadPreloadedData = LoadPreloadedData(repository),
                topSeller = TopSeller(repository),
            )
    }
}