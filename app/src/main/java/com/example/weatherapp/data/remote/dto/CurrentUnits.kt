package com.example.weatherapp.data.remote.dto

data class CurrentUnits(
    val interval: String,
    val is_day: String,
    val rain: String,
    val temperature_2m: String,
    val time: String,
    val wind_speed_10m: String,
    val cloud_cover: String
)