package com.example.weatherapp.data.remote.api

import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.data.remote.dto.WeatherDto
import kotlinx.coroutines.delay
import java.io.IOException

class FakeIOErrorApi: WeatherApi {
    override suspend fun getWeather(
        latitude: String,
        longitude: String,
        current: String,
        daily: String,
        hourly: String,
        pastDays: Int,
        timezone: String
    ): WeatherDto {
        delay(1000)
        throw IOException()
    }
}