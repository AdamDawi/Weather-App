package com.example.weatherapp.presentation.main_screen

import com.example.weatherapp.domain.model.Weather

data class MainState (
    val weather: Weather? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)