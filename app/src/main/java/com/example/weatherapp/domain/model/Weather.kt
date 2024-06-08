package com.example.weatherapp.domain.model

import com.example.weatherapp.data.remote.dto.Current
import com.example.weatherapp.data.remote.dto.CurrentUnits

data class Weather(
    val latitude: Double,
    val longitude: Double,
    val current: Current,
    val current_units: CurrentUnits,
)
