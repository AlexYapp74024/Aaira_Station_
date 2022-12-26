package com.example.aairastation.feature_settings.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aairastation.feature_settings.domain.use_cases.SettingsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingScreenViewModel @Inject constructor(
    private val useCases: SettingsUseCases
) : ViewModel() {
    fun injectPreloadedData() = viewModelScope.launch{
        useCases.loadPreloadedData()
    }
}