package com.example.weatherapp.domain.model

data class Weather(
    val temperature_2m: List<Double>,
    val time: List<String>,
)
