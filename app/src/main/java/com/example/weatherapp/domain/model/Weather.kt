package com.example.weatherapp.domain.model

import com.example.weatherapp.data.remote.dto.Current
import com.example.weatherapp.data.remote.dto.CurrentUnits
import com.example.weatherapp.data.remote.dto.Daily
import com.example.weatherapp.data.remote.dto.DailyUnits
import com.example.weatherapp.data.remote.dto.Hourly
import com.example.weatherapp.data.remote.dto.HourlyUnits

data class Weather(
    val latitude: Double,
    val longitude: Double,
    val current: Current,
    val current_units: CurrentUnits,
    val daily: Daily,
    val daily_units: DailyUnits,
    val hourly: Hourly,
    val hourly_units: HourlyUnits
)
