package com.example.weatherapp.data.remote.dto

import com.example.weatherapp.domain.model.Weather

data class WeatherDto(
    val elevation: Int,
    val generationtime_ms: Double,
    val hourly: Hourly,
    val hourly_units: HourlyUnits,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val timezone_abbreviation: String,
    val utc_offset_seconds: Int
)

fun WeatherDto.toWeather(): Weather {
    return Weather(
        hourly.temperature_2m,
        hourly.time
    )
}