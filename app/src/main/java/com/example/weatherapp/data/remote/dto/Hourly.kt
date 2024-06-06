package com.example.weatherapp.data.remote.dto

data class Hourly(
    val temperature_2m: List<Double>,
    val time: List<String>
)