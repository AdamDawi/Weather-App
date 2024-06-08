package com.example.weatherapp.data.remote.dto

data class Current(
    val interval: Int,
    val is_day: Int,
    val rain: Int,
    val temperature_2m: Double,
    val time: String,
    val wind_speed_10m: Double
)