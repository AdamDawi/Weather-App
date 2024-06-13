package com.example.weatherapp.data.remote.dto

data class Hourly(
    val relative_humidity_2m: List<Int>,
    val temperature_2m: List<Double>,
    val time: List<String>,
    val weather_code: List<Int>,
    val wind_speed_10m: List<Double>
)