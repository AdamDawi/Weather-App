package com.example.weatherapp.data.remote

import com.example.weatherapp.data.remote.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi{
    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("current") current: String = "temperature_2m,is_day,rain,cloud_cover,wind_speed_10m,weather_code,relative_humidity_2m",
        @Query("timezone") timezone: String = "auto"
    ): WeatherDto
}