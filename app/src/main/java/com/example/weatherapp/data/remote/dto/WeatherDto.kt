package com.example.weatherapp.data.remote.dto

import com.example.weatherapp.domain.model.Weather

data class WeatherDto(
    val current: Current?,
    val current_units: CurrentUnits?,
    val elevation: Int,
    val generationtime_ms: Double,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val timezone_abbreviation: String,
    val utc_offset_seconds: Int
)

fun WeatherDto.toWeather(): Weather {
    return Weather(
        latitude = latitude,
        longitude = longitude,
        current = current,
        current_units = current_units
    )
}