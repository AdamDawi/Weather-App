package com.example.weatherapp.data.remote.dto

data class HourlyUnits(
    val relative_humidity_2m: String,
    val temperature_2m: String,
    val time: String,
    val weather_code: String,
    val wind_speed_10m: String
)