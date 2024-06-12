package com.example.weatherapp.data.remote.api

import com.example.weatherapp.common.mockWeather
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.data.remote.dto.WeatherDto
import kotlinx.coroutines.delay

class FakeSuccessApi: WeatherApi{
    override suspend fun getWeather(
        latitude: String,
        longitude: String,
        current: String,
        timezone: String
    ): WeatherDto {
        delay(1000)
        return mockWeather
    }
}