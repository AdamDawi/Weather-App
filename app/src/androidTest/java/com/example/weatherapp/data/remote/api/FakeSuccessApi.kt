package com.example.weatherapp.data.remote.api

import com.example.weatherapp.common.mockWeather
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.data.remote.dto.WeatherDto

class FakeSuccessApi: WeatherApi{

    override suspend fun getWeather(
        latitude: String,
        longitude: String,
        current: String,
        daily: String,
        hourly: String,
        pastDays: Int,
        timezone: String
    ): WeatherDto {
        return mockWeather
    }
}